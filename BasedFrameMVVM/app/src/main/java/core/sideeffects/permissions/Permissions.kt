package core.sideeffects.permissions

import core.model.tasks.Task
import core.sideeffects.permissions.plugin.PermissionStatus

interface Permissions {

    /**
     * Whether the app has the specified permission or not.
     */
    fun hasPermissions(permission: String): Boolean

    /**
     * Request the specified permission.
     * See [PermissionStatus]
     */
    suspend fun requestPermission(permission: String): PermissionStatus

}