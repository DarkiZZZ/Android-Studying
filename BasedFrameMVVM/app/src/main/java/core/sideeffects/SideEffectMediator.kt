package core.sideeffects

import core.model.dispatchers.Dispatcher
import core.model.dispatchers.MainThreadDispatcher
import core.utils.ResourceActions

open class SideEffectMediator<Implementation>(
    dispatcher: Dispatcher = MainThreadDispatcher()
) {

    protected val target = ResourceActions<Implementation>(dispatcher)

    fun setTarget(target: Implementation?){
        this.target.resource = target
    }

    fun clear(){
        target.clear()
    }
}