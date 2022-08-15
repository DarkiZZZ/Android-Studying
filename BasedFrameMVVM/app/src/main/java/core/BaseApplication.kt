package core

import core.model.Repository

interface BaseApplication {

    val repositories: List<Repository>
}