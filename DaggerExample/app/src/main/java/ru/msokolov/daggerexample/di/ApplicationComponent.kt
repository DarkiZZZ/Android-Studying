package ru.msokolov.daggerexample.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.msokolov.daggerexample.presentation.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance timeMillis: Long
        ): ApplicationComponent
    }
}