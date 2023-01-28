package ru.msokolov.daggerexample

import android.app.Application
import ru.msokolov.daggerexample.di.DaggerApplicationComponent

class ExampleApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this, System.currentTimeMillis())
    }
}