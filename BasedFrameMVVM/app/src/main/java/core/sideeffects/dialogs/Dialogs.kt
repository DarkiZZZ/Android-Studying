package core.sideeffects.dialogs

import core.model.tasks.Task
import core.sideeffects.dialogs.plugin.DialogConfig

interface Dialogs {

    fun show(dialogConfig: DialogConfig): Task<Boolean>
}