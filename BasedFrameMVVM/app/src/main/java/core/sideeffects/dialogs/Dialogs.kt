package core.sideeffects.dialogs

import core.sideeffects.dialogs.plugin.DialogConfig

interface Dialogs {

    suspend fun show(dialogConfig: DialogConfig): Boolean
}