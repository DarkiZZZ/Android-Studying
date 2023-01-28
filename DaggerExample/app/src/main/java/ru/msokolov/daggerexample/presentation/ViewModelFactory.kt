package ru.msokolov.daggerexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.msokolov.daggerexample.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<String, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass.simpleName]?.get() as T
    }
}