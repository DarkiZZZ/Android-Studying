package ru.msokolov.daggerexample.presentation

import androidx.lifecycle.ViewModel
import ru.msokolov.daggerexample.domain.ExampleUseCase
import javax.inject.Inject

class ExampleViewModel @Inject constructor(
    private val useCase: ExampleUseCase
): ViewModel() {

    fun method() {
        useCase.invoke()
    }
}
