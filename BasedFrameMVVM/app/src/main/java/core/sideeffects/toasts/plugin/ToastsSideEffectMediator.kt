package core.sideeffects.toasts.plugin

import android.content.Context
import android.widget.Toast
import core.model.tasks.dispatchers.MainThreadDispatcher
import core.sideeffects.SideEffectMediator
import core.sideeffects.toasts.Toasts

class ToastsSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<Nothing>(), Toasts {

    private val dispatcher = MainThreadDispatcher()

    override fun toast(message: String) {
        dispatcher.dispatch {
            Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
        }
    }

}