package core.sideeffects

import android.content.Context

@Suppress("UNCHECKED_CAST")
class SideEffectMediatorsHolder {

    private val _mediators = mutableMapOf<Class<*>, SideEffectMediator<*>>()
    val mediators: List<SideEffectMediator<*>>
        get() = _mediators.values.toList()

    /**
     * Whether the [SideEffectMediator] of the specified class exists or not.
     */
    fun <T> contains(clazz: Class<T>): Boolean {
        return _mediators.contains(clazz)
    }

    /**
     * Create and store [SideEffectMediator] by using the specified [SideEffectPlugin].
     */
    fun <Mediator, Implementation> putWithPlugin(
        applicationContext: Context,
        plugin: SideEffectPlugin<Mediator, Implementation>
    ) {
        _mediators[plugin.mediatorClass] = plugin.createMediator(applicationContext)
    }

    /**
     * Tie [SideEffectImplementation] with the [SideEffectMediator]. So mediator can deliver all calls
     * to the implementation.
     */
    fun <Mediator, Implementation> setTargetWithPlugin(
        plugin: SideEffectPlugin<Mediator, Implementation>,
        sideEffectImplementationsHolder: SideEffectImplementationsHolder,
    ) {
        val intermediateViewService = get(plugin.mediatorClass)
        val target = sideEffectImplementationsHolder.getWithPlugin(plugin)
        if (intermediateViewService is SideEffectMediator<*>) {
            (intermediateViewService as SideEffectMediator<Implementation>).setTarget(target)
        }
    }

    /**
     * Get the [SideEffectMediator] instance by its class.
     */
    fun <T> get(clazz: Class<T>): T {
        return _mediators[clazz] as T
    }

    /**
     * Untie all [SideEffectImplementation] instances from all [SideEffectMediator] instances.
     */
    fun removeTargets() {
        _mediators.values.forEach { it.setTarget(null) }
    }

    /**
     * Clean-up all mediators.
     */
    fun clear() {
        _mediators.values.forEach { it.clear() }
        _mediators.clear()
    }

}