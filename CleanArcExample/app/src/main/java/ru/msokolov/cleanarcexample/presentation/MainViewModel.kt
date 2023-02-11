package ru.msokolov.cleanarcexample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.usecase.GetUserNameUseCase
import ru.msokolov.cleanarcexample.domain.usecase.SaveUserNameUseCase
import ru.msokolov.cleanarcexample.presentation.State.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNameUseCase: GetUserNameUseCase, private val saveNameUseCase: SaveUserNameUseCase
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    fun getName() {
        _state.value = Result(getNameUseCase())
    }

    fun saveName(saveParam: SaveUserNameParam) {
        if (saveNameUseCase(saveParam)) {
            _state.value = Success
        } else {
            _state.value = Error
        }
    }
}