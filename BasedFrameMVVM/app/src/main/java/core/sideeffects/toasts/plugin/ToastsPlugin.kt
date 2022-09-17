package core.sideeffects.toasts.plugin

import android.content.Context
import core.sideeffects.SideEffectMediator
import core.sideeffects.SideEffectPlugin
import core.sideeffects.toasts.Toasts

class ToastsPlugin : SideEffectPlugin<Toasts, Nothing> {

    override val mediatorClass: Class<Toasts>
    get() = Toasts::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        return ToastsSideEffectMediator(applicationContext)
    }

}