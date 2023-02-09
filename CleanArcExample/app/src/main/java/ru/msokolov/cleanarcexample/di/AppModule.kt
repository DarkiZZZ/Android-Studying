package ru.msokolov.cleanarcexample.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.msokolov.cleanarcexample.presentation.MainViewModel

val appModule = module {

    viewModel<MainViewModel> {
        MainViewModel(getNameUseCase = get(), saveNameUseCase = get())
    }
}