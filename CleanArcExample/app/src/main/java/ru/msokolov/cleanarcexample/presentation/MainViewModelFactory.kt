package ru.msokolov.cleanarcexample.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.msokolov.cleanarcexample.data.repository.UserRepositoryImpl
import ru.msokolov.cleanarcexample.data.storage.sharedPrefs.SharedPrefUserStorageImpl
import ru.msokolov.cleanarcexample.domain.usecase.GetUserNameUseCase
import ru.msokolov.cleanarcexample.domain.usecase.SaveUserNameUseCase

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {


    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        UserRepositoryImpl(SharedPrefUserStorageImpl(context = context))
    }
    private val getNameUseCase by lazy {
        GetUserNameUseCase(userRepository = userRepository)
    }
    private val saveNameUseCase by lazy {
        SaveUserNameUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getNameUseCase = getNameUseCase,
                saveNameUseCase = saveNameUseCase) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}