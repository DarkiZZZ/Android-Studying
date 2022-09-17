package core

import androidx.lifecycle.ViewModel
import core.sideeffects.SideEffectMediator
import core.sideeffects.SideEffectMediatorsHolder
const val ARG_SCREEN = "ARG_SCREEN"

class ActivityScopeViewModel: ViewModel(){

    internal val sideEffectMediatorHolder = SideEffectMediatorsHolder()

    val sideEffectMediators: List<SideEffectMediator<*>>
        get() = sideEffectMediatorHolder.mediators

    override fun onCleared() {
        super.onCleared()
        sideEffectMediatorHolder.clear()
    }

}
