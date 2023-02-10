package ru.msokolov.cleanarcexample.di

import dagger.Component
import ru.msokolov.cleanarcexample.presentation.MainActivity

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}