package ru.msokolov.daggerexample.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.msokolov.daggerexample.di.IdQualifier
import ru.msokolov.daggerexample.di.NameQualifier
import ru.msokolov.daggerexample.domain.ExampleUseCase
import javax.inject.Inject
import javax.inject.Named

class ExampleViewModel @Inject constructor(
    private val useCase: ExampleUseCase,
    @IdQualifier private val id: String,
    @NameQualifier private val name: String
): ViewModel() {

    fun method() {
        useCase.invoke()
        Log.d("ExampleViewModel", "$this $id $name")
    }
}
