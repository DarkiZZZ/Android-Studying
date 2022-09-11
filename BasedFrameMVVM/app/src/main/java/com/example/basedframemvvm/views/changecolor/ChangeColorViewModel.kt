package com.example.basedframemvvm.views.changecolor

import androidx.lifecycle.*
import com.example.basedframemvvm.R
import com.example.basedframemvvm.model.colors.ColorsRepository
import com.example.basedframemvvm.model.colors.NamedColor
import core.sideeffects.navigator.Navigator
import core.views.BaseViewModel
import com.example.basedframemvvm.views.changecolor.ChangeColorFragment.*
import core.model.PendingResult
import core.model.SuccessResult
import core.sideeffects.resources.Resources
import core.sideeffects.toasts.Toasts
import core.views.LiveResult
import core.views.MediatorLiveResult
import core.views.MutableLiveResult
import kotlinx.coroutines.CancellationException
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
    private val _availableColors = MutableLiveResult<List<NamedColor>>(PendingResult())
    private val _currentColorId = savedStateHandle.getLiveData("currentColorId", screen.currentColorId)
    private val _saveInProgress = MutableLiveData(false)

    // main destination (contains merged values from _availableColors & _currentColorId)
    private val _viewState = MediatorLiveResult<ViewState>()
    val viewState: LiveResult<ViewState> = _viewState

    // Using Transformations.map() to get dynamic screen title
    val screenTitle: LiveData<String> = Transformations.map(viewState){ result ->
        if (result is SuccessResult){
            val currentColor = result.data.colorsList.first { it.selected }
            resources.getString(R.string.change_color_screen_title, currentColor.namedColor.name)
        }
        else{
            resources.getString(R.string.change_color_screen_title_default)
        }
    }

    init {
        load()

        // initializing MediatorLiveData
        _viewState.addSource(_availableColors) { mergeSources() }
        _viewState.addSource(_currentColorId) { mergeSources() }
        _viewState.addSource(_saveInProgress) {mergeSources()}
    }

    override fun onColorChosen(namedColor: NamedColor) {
        if (_saveInProgress.value == true) return
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed()  = viewModelScope.launch {
        _saveInProgress.postValue(true)
        try {
            val currentColorId =
                _currentColorId.value ?: throw IllegalStateException("Color ID have to be null")
            val currentColor = colorsRepository.getById(currentColorId)
            colorsRepository.setCurrentColor(currentColor)

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
    private fun mergeSources() {
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        val saveInProgress = _saveInProgress.value ?: return
        _viewState.value = colors.map { colorsList ->
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