package ru.msokolov.daggerexample.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.msokolov.daggerexample.presentation.ExampleViewModel
import ru.msokolov.daggerexample.presentation.ExampleViewModel2

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("ExampleViewModel")
    @Binds
    fun bindExampleViewModel(impl: ExampleViewModel): ViewModel

    @IntoMap
    @StringKey("ExampleViewModel2")
    @Binds
    fun bindExampleViewModel2(impl: ExampleViewModel2): ViewModel
}