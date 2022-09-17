package com.example.basedframemvvm.views.changecolor

import androidx.lifecycle.*
import com.example.basedframemvvm.R
import com.example.basedframemvvm.model.colors.ColorsRepository
import com.example.basedframemvvm.model.colors.NamedColor
import core.sideeffects.navigator.Navigator
import core.views.BaseViewModel
import com.example.basedframemvvm.views.changecolor.ChangeColorFragment.*
import core.model.*
import core.sideeffects.resources.Resources
import core.sideeffects.toasts.Toasts
import core.utils.finiteSharedIn
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ChangeColorViewModel(
    screen: Screen,
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val colorsRepository: ColorsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), ColorsAdapter.Listener {

    // input sources
    private val _availableColors = MutableStateFlow<Result<List<NamedColor>>>(PendingResult())
    private val _currentColorId = savedStateHandle.getMutableStateFlow("currentColorId", screen.currentColorId)
    private val _instantSaveInProgress = MutableStateFlow<Progress>(EmptyProgress)
    private val _sampledSaveInProgress = MutableStateFlow<Progress>(EmptyProgress)

    // main destination (contains merged values from _availableColors & _currentColorId)
    val viewState: Flow<Result<ViewState>> = combine(
        _availableColors,
        _currentColorId,
        _instantSaveInProgress,
        _sampledSaveInProgress,
        ::mergeSources
    )

    // Using Transformations.map() to get dynamic screen title
    val screenTitle: LiveData<String> = viewState
        .map { result ->
            return@map if (result is SuccessResult){
                val currentColor = result.data.colorsList.first { it.selected }
                resources.getString(R.string.change_color_screen_title, currentColor.namedColor.name)
            }
            else{
                resources.getString(R.string.change_color_screen_title_default)
            }
        }
        .asLiveData()

    init {
        load()
    }

    override fun onColorChosen(namedColor: NamedColor) {
        if (_instantSaveInProgress.value.isInProgress()) return
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed()  = viewModelScope.launch {
        try {
            _instantSaveInProgress.value = PercentageProgress.START
            _sampledSaveInProgress.value = PercentageProgress.START

            val currentColorId = _currentColorId.value
            val currentColor = colorsRepository.getById(currentColorId)


            val flow = colorsRepository.setCurrentColor(currentColor)
                .finiteSharedIn(this)

            val instantJob = async {
                flow.collect{ percentage ->
                    _instantSaveInProgress.value = PercentageProgress(percentage)
                }
            }

            val sampledJob = async {
                flow.sample(150).collect{ percentage ->
                    _sampledSaveInProgress.value = PercentageProgress(percentage)
                }
            }
            instantJob.await()
            sampledJob.await()


            navigator.goBack(currentColor)
        }
        catch (e: Exception){
            if (e !is CancellationException){
                toasts.toast(resources.getString(R.string.error_message_2))
            }
        }
        finally {
            _instantSaveInProgress.value = EmptyProgress
            _sampledSaveInProgress.value = EmptyProgress
        }
    }

    fun tryAgain(){
        load()
    }

    fun onCancelPressed() {
        navigator.goBack()
    }

    /**
     * [MediatorLiveData] can listen other LiveData instances (even more than 1)
     * and combine their values.
     * Here we listen the list of available colors ([_availableColors] live-data) + current color id
     * ([_currentColorId] live-data), then we use both of these values in order to create a list of
     * [NamedColorListItem], it is a list to be displayed in RecyclerView.
     */
    private fun mergeSources(colors: Result<List<NamedColor>>, currentColorId: Long,
                             instantSaveInProgress: Progress, sampledSaveInProgress: Progress ) : Result<ViewState>{

        return colors.map { colorsList ->
            ViewState(
                colorsList = colorsList.map { NamedColorListItem(it, currentColorId == it.id) },
                showSaveButton = !instantSaveInProgress.isInProgress(),
                showCancelButton = !instantSaveInProgress.isInProgress(),
                showSaveProgressBar = instantSaveInProgress.isInProgress(),

                saveProgressPercentage = instantSaveInProgress.getPercentage(),
                saveProgressPercentageMessage = resources
                    .getString(R.string.percentage_value, sampledSaveInProgress.getPercentage())
            )
        }
    }

    private fun load() = into(_availableColors){
        colorsRepository.getAvailableColors()
    }

    data class ViewState(
        val colorsList: List<NamedColorListItem>,
        val showSaveButton: Boolean,
        val showCancelButton: Boolean,
        val showSaveProgressBar: Boolean,
        val saveProgressPercentage: Int,
        val saveProgressPercentageMessage: String
    )

}