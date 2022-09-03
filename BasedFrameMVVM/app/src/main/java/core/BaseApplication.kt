package core

import core.model.Repository

interface BaseApplication {

    val singletonScopeDependencies: List<Any>
}