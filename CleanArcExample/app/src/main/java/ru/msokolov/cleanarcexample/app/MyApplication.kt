package ru.msokolov.cleanarcexample.app

import android.app.Application
import dagger.internal.DaggerGenerated
import ru.msokolov.cleanarcexample.di.AppComponent
import ru.msokolov.cleanarcexample.di.AppModule
import ru.msokolov.cleanarcexample.di.DaggerAppComponent

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .build()
    }
}