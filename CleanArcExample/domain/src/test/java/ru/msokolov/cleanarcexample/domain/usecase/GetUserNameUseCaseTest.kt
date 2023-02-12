package ru.msokolov.cleanarcexample.domain.usecase

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.msokolov.cleanarcexample.domain.models.UserName
import ru.msokolov.cleanarcexample.domain.repository.UserRepository


class GetUserNameUseCaseTest {

    private val userRepository = mock<UserRepository>()

    @Test
    fun shouldReturnCorrectData(){
        val testUserName = UserName(firstName = "test first name", lastName = "test last name")
        Mockito.`when`(userRepository.getName()).thenReturn(testUserName)

        val useCase = GetUserNameUseCase(userRepository = userRepository)
        val actual = useCase()
        val expected = UserName(firstName = "test first name", lastName = "test last name")
        Assertions.assertEquals(expected, actual)
    }
}