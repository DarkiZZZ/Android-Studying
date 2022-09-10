package core.sideeffects.permissions.plugin

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import core.model.ErrorResult
import core.model.tasks.Task
import core.model.tasks.callback.CallbackTask
import core.model.tasks.callback.Emitter
import core.sideeffects.SideEffectMediator
import core.sideeffects.permissions.Permissions

class PermissionsSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<PermissionsSideEffectImpl>(), Permissions {

    val retainedState = RetainedState()

    override fun hasPermissions(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(appContext, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission(permission: String): Task<PermissionStatus> = CallbackTask.create { emitter ->
        if (retainedState.emitter != null) {
            emitter.emit(ErrorResult(IllegalStateException("Only one permission request can be active")))
            return@create
        }
        retainedState.emitter = emitter
        target { implementation ->
            implementation.requestPermission(permission)
        }
    }

    class RetainedState(
        var emitter: Emitter<PermissionStatus>? = null
    )

}