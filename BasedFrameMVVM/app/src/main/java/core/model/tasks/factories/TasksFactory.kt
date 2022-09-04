package core.model.tasks.factories

import core.model.tasks.Task

typealias TaskBody<T> = () -> T

interface TasksFactory{

    fun <T> async(body: TaskBody<T>): Task<T>
}