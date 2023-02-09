package ru.msokolov.cleanarcexample.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.msokolov.cleanarcexample.di.appModule
import ru.msokolov.cleanarcexample.di.dataModule
import ru.msokolov.cleanarcexample.di.domainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}