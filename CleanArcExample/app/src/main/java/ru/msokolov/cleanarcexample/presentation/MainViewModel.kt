package ru.msokolov.cleanarcexample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.repository.UserRepository
import ru.msokolov.cleanarcexample.domain.usecase.GetUserNameUseCase
import ru.msokolov.cleanarcexample.domain.usecase.SaveUserNameUseCase
import ru.msokolov.cleanarcexample.presentation.State.*

class MainViewModel(private val userRepository: ru.msokolov.cleanarcexample.domain.repository.UserRepository) : ViewModel() {

    private val getNameUseCase =
        ru.msokolov.cleanarcexample.domain.usecase.GetUserNameUseCase(userRepository)
    private val saveNameUseCase =
        ru.msokolov.cleanarcexample.domain.usecase.SaveUserNameUseCase(userRepository)

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    fun getName() {
        _state.value = Result(getNameUseCase())
    }

    fun saveName(saveParam: ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam) {
        if (saveNameUseCase(saveParam)) {
            _state.value = Success
        } else {
            _state.value = Error
        }
    }
}