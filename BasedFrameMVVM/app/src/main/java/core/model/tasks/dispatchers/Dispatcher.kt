package core.model.tasks.dispatchers

interface Dispatcher {

    fun dispatch(block: () -> Unit) // param BLOCK - some block of code
}