package ru.msokolov.daggerexample.di

import dagger.Binds
import dagger.Module
import ru.msokolov.daggerexample.data.datasource.ExampleLocalDataSource
import ru.msokolov.daggerexample.data.datasource.ExampleLocalDataSourceImpl
import ru.msokolov.daggerexample.data.datasource.ExampleRemoteDataSource
import ru.msokolov.daggerexample.data.datasource.ExampleRemoteDataSourceImpl

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindLocalDataSource(impl: ExampleLocalDataSourceImpl): ExampleLocalDataSource

    @ApplicationScope
    @Binds
    fun bindRemoteDataSource(impl: ExampleRemoteDataSourceImpl): ExampleRemoteDataSource
}