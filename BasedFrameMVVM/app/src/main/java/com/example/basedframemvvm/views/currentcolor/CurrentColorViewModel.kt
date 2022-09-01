package com.example.basedframemvvm.views.currentcolor

import androidx.lifecycle.viewModelScope
import com.example.basedframemvvm.R
import com.example.basedframemvvm.model.colors.ColorListener
import com.example.basedframemvvm.model.colors.ColorsRepository
import com.example.basedframemvvm.model.colors.NamedColor
import core.navigator.Navigator
import core.uiactions.UiActions
import core.views.BaseViewModel
import com.example.basedframemvvm.views.changecolor.ChangeColorFragment
import core.model.ErrorResult
import core.model.PendingResult
import core.model.SuccessResult
import core.model.takeSuccess
import core.views.LiveResult
import core.views.MutableLiveResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val colorsRepository: ColorsRepository
) : BaseViewModel() {

    private val _currentColor = MutableLiveResult<NamedColor>(PendingResult())
    val currentColor: LiveResult<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(SuccessResult(it)) // <-- hardcoding to get initial Success result every launch(with Error on 44 line -
                                                    // on the second time
    }

    // --- example of listening results via model layer

    init {
        viewModelScope.launch {
            delay(2000)
                //colorsRepository.addListener(colorListener)
            _currentColor.postValue(ErrorResult(RuntimeException())) // hardcoding to fail Pending & test tryAgain container
        }
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    // --- example of listening results directly from the screen

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
        }
    }

    // ---

    fun changeColor() {
        val currentColor = currentColor.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }

    fun tryAgain(){
        viewModelScope.launch {
            _currentColor.postValue(PendingResult()) // <-- hardcoding to test Pending after clicking on TryAgain button
            delay(2000)
            colorsRepository.addListener(colorListener)
        }
    }

}

