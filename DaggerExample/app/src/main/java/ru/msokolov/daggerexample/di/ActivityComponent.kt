package ru.msokolov.daggerexample.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.msokolov.daggerexample.presentation.MainActivity
import ru.msokolov.daggerexample.presentation.MainActivity2

@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {


    fun inject(activity: MainActivity)
    fun inject(activity: MainActivity2)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance id: String
        ): ActivityComponent
    }
}