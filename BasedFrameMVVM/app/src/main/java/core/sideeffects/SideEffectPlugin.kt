package core.sideeffects

import android.content.Context

interface SideEffectPlugin<Mediator, Implementation> {

    val mediatorClass: Class<Mediator>

    fun createMediator(applicationContext: Context): SideEffectMediator<Implementation>

    fun createImplementation(mediator: Mediator): Implementation? = null
}