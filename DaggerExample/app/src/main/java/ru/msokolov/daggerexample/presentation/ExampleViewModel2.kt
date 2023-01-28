package ru.msokolov.daggerexample.presentation

import androidx.lifecycle.ViewModel
import ru.msokolov.daggerexample.domain.ExampleRepository
import javax.inject.Inject

class ExampleViewModel2 @Inject constructor(
    private val repository: ExampleRepository
): ViewModel() {

    fun method() {
        repository.method()
    }
}

