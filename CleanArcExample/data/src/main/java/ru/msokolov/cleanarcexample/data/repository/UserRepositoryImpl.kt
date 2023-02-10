package ru.msokolov.cleanarcexample.data.repository

import ru.msokolov.cleanarcexample.data.storage.models.User
import ru.msokolov.cleanarcexample.data.storage.UserStorage
import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.models.UserName
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) :
    ru.msokolov.cleanarcexample.domain.repository.UserRepository {



    override fun saveName(saveParam: ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam): Boolean {
        val user = User(firstName = saveParam.name, lastName = "")
        return userStorage.save(user)
    }

    override fun getName(): ru.msokolov.cleanarcexample.domain.models.UserName {
        val user = userStorage.get()
        return ru.msokolov.cleanarcexample.domain.models.UserName(
            firstName = user.firstName,
            lastName = user.lastName
        )
    }
}