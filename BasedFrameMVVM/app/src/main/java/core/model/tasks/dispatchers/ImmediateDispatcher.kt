package core.model.tasks.dispatchers

class ImmediateDispatcher: Dispatcher {

    override fun dispatch(block: () -> Unit) {
        block()
    }
}