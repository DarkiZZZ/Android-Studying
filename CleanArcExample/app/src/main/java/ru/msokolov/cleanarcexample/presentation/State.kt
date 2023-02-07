package ru.msokolov.cleanarcexample.presentation

import ru.msokolov.cleanarcexample.domain.models.UserName

sealed class State{
    object Success: State()
    object Error: State()
    class Result(val userName: UserName): State()
}