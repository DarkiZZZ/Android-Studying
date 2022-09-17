package core.sideeffects.navigator.plugin

import android.content.Context
import core.sideeffects.SideEffectMediator
import core.sideeffects.SideEffectPlugin
import core.sideeffects.navigator.Navigator

class NavigatorPlugin(
    private val navigator: Navigator,
) : SideEffectPlugin<Navigator, Navigator> {

    override val mediatorClass: Class<Navigator>
        get() = Navigator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Navigator> {
        return NavigatorSideEffectMediator()
    }

    override fun createImplementation(mediator: Navigator): Navigator {
        return navigator
    }

}