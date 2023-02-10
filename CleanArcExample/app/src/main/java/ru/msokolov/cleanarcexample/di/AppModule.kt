package ru.msokolov.cleanarcexample.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context{
        return context
    }
}