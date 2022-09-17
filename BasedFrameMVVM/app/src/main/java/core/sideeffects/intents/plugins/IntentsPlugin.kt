package core.sideeffects.intents.plugins

import android.content.Context
import core.sideeffects.SideEffectMediator
import core.sideeffects.SideEffectPlugin
import core.sideeffects.intents.Intents

class IntentsPlugin : SideEffectPlugin<Intents, Nothing> {

    override val mediatorClass: Class<Intents>
        get() = Intents::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        return IntentsSideEffectMediator(applicationContext)
    }

}