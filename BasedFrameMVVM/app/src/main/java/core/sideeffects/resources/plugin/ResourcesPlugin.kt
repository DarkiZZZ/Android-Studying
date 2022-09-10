package core.sideeffects.resources.plugin

import android.content.Context
import core.sideeffects.SideEffectMediator
import core.sideeffects.SideEffectPlugin

class ResourcesPlugin: SideEffectPlugin<ResourcesSideEffectMediator, Nothing> {

    override val mediatorClass: Class<ResourcesSideEffectMediator>
        get() = ResourcesSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing>{
        return ResourcesSideEffectMediator(applicationContext)
    }
}