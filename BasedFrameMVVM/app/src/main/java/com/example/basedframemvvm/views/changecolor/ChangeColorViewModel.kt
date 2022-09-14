package com.example.basedframemvvm.views.changecolor

import androidx.lifecycle.*
import com.example.basedframemvvm.R
import com.example.basedframemvvm.model.colors.ColorsRepository
import com.example.basedframemvvm.model.colors.NamedColor
import core.sideeffects.navigator.Navigator
import core.views.BaseViewModel
import com.example.basedframemvvm.views.changecolor.ChangeColorFragment.*
import core.model.PendingResult
import core.model.Result
import core.model.SuccessResult
import core.sideeffects.resources.Resources
import core.sideeffects.toasts.Toasts
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalStateException


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
    private val _saveInProgress = MutableStateFlow(false)

    // main destination (contains merged values from _availableColors & _currentColorId)
    val viewState: Flow<Result<ViewState>> = combine(
        _availableColors,
        _currentColorId,
        _saveInProgress,
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
        if (_saveInProgress.value) return
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed()  = viewModelScope.launch {
        _saveInProgress.value = true
        try {
            val currentColorId =
                _currentColorId.value ?: throw IllegalStateException("Color ID have to be null")
            val currentColor = colorsRepository.getById(currentColorId)
            colorsRepository.setCurrentColor(currentColor).collect()

            navigator.goBack(currentColor)
        }
        catch (e: Exception){
            if (e !is CancellationException){
                toasts.toast(resources.getString(R.string.error_message_2))
            }
        }
        finally {
            _saveInProgress.value = false
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
    private fun mergeSources(colors: Result<List<NamedColor>>, currentColorId: Long, saveInProgress: Boolean) : Result<ViewState>{

        return colors.map { colorsList ->
            ViewState(
                colorsList = colorsList.map { NamedColorListItem(it, currentColorId == it.id) },
                showSaveButton = !saveInProgress,
                showCancelButton = !saveInProgress,
                showSaveProgressBar = saveInProgress
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
        val showSaveProgressBar: Boolean
    )

}