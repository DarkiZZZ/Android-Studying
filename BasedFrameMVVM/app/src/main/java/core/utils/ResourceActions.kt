package core.utils

import core.model.dispatchers.Dispatcher

typealias ResourceAction<T> = (T) -> Unit

class ResourceActions<T>(
    private val dispatcher: Dispatcher
) {

    var resource: T? = null
        set(newValue) {
            field = newValue
            if (newValue != null){
                actions.forEach { action ->
                    dispatcher.dispatch {
                        action(newValue)
                    }
                }
                actions.clear()
            }
        }

    private val actions = mutableListOf<ResourceAction<T>>()

    operator fun invoke(action: ResourceAction<T>){
        val resource = this.resource
        if (resource == null){
            actions += action
        }
        else{
            dispatcher.dispatch {
                action(resource)
            }
        }
    }

    fun clear(){
        actions.clear()
    }

}