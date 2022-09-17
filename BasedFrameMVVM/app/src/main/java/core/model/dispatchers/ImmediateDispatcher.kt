package core.model.dispatchers

class ImmediateDispatcher: Dispatcher {

    override fun dispatch(block: () -> Unit) {
        block()
    }
}