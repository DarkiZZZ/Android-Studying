package com.example.basedframemvvm

import android.os.Bundle
import com.example.basedframemvvm.views.currentcolor.CurrentColorFragment
import core.sideeffects.SideEffectPluginsManager
import core.sideeffects.dialogs.plugin.DialogsPlugin
import core.sideeffects.intents.plugins.IntentsPlugin
import core.sideeffects.navigator.plugin.NavigatorPlugin
import core.sideeffects.navigator.plugin.StackFragmentNavigator
import core.sideeffects.permissions.plugin.PermissionsPlugin
import core.sideeffects.resources.plugin.ResourcesPlugin
import core.sideeffects.toasts.plugin.ToastsPlugin
import core.views.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun registerPlugins(manager: SideEffectPluginsManager) = with (manager) {
        val navigator = createNavigator()
        register(ToastsPlugin())
        register(ResourcesPlugin())
        register(NavigatorPlugin(navigator))
        register(PermissionsPlugin())
        register(DialogsPlugin())
        register(IntentsPlugin())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Initializer.initDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun createNavigator() = StackFragmentNavigator(
            containerId = R.id.fragmentContainer,
            defaultTitle = getString(R.string.app_name),
            animations = StackFragmentNavigator.Animations(
                animEnter = R.anim.enter,
                animExit = R.anim.exit,
                animPopEnter = R.anim.pop_enter,
                animPopExit = R.anim.pop_exit
            ),
            initialScreenCreator = {CurrentColorFragment.Screen()}
    )
}