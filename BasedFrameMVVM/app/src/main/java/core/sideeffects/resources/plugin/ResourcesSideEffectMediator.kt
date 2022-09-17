package core.sideeffects.resources.plugin

import android.content.Context
import core.sideeffects.SideEffectMediator
import core.sideeffects.resources.Resources

class ResourcesSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<Nothing>(), Resources{

    override fun getString(resId: Int, vararg args: Any): String {
        return appContext.getString(resId, *args)
    }
}