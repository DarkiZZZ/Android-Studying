package ru.msokolov.cleanarcexample.domain.usecase

import ru.msokolov.cleanarcexample.domain.models.UserName
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

class GetUserNameUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): UserName {
        return userRepository.getName()
    }
}