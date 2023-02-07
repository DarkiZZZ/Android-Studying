package ru.msokolov.cleanarcexample.domain.usecase

import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

class SaveUserNameUseCase(private val userRepository: UserRepository) {

    operator fun invoke(param: SaveUserNameParam): Boolean {
        val oldUserName = userRepository.getName()
        if (oldUserName.firstName == param.name) {
            return true
        }
        return userRepository.saveName(saveParam = param)
    }

}