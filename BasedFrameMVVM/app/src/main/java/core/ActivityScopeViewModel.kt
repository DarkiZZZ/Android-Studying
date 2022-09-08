package core

import androidx.lifecycle.ViewModel
import core.sideeffects.navigator.IntermediateNavigator
import core.sideeffects.navigator.Navigator
import core.uiactions.UiActions

const val ARG_SCREEN = "ARG_SCREEN"

class ActivityScopeViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
): ViewModel(), Navigator by navigator, UiActions by uiActions{

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }

}
