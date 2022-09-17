package core.sideeffects.dialogs.plugin

import android.content.Context
import core.sideeffects.SideEffectMediator 
import core.sideeffects.SideEffectPlugin

class DialogsPlugin: SideEffectPlugin<DialogsSideEffectMediator, DialogsSideEffectImpl> {

    override val mediatorClass: Class<DialogsSideEffectMediator>
        get() = DialogsSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<DialogsSideEffectImpl>{
        return DialogsSideEffectMediator()
    }

    override fun createImplementation(mediator: DialogsSideEffectMediator): DialogsSideEffectImpl? {
        return DialogsSideEffectImpl(mediator.retainedState)
    }
}
