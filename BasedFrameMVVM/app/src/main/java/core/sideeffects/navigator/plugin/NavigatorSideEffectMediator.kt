package core.sideeffects.navigator.plugin

import core.sideeffects.SideEffectMediator
import core.sideeffects.navigator.Navigator
import core.views.BaseScreen

class NavigatorSideEffectMediator : SideEffectMediator<Navigator>(), Navigator {

    override fun launch(screen: BaseScreen) = target {
        it.launch(screen)
    }

    override fun goBack(result: Any?) = target {
        it.goBack(result)
    }

}