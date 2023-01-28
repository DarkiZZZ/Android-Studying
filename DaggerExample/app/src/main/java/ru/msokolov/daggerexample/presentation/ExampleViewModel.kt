package ru.msokolov.daggerexample.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.msokolov.daggerexample.domain.ExampleUseCase
import javax.inject.Inject

class ExampleViewModel @Inject constructor(
    private val useCase: ExampleUseCase,
    private val id: String
): ViewModel() {

    fun method() {
        useCase.invoke()
        Log.d("ExampleViewModel", "$this $id")
    }
}
