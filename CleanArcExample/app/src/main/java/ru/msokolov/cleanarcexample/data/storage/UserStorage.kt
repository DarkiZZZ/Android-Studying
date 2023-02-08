package ru.msokolov.cleanarcexample.data.storage

import ru.msokolov.cleanarcexample.data.storage.models.User

interface UserStorage {

    fun save(user: User): Boolean

    fun get(): User
}