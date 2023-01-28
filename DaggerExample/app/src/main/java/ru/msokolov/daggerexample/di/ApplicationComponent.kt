package ru.msokolov.daggerexample.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.msokolov.daggerexample.presentation.MainActivity
import ru.msokolov.daggerexample.presentation.MainActivity2

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: MainActivity2)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance timeMillis: Long
        ): ApplicationComponent
    }
}