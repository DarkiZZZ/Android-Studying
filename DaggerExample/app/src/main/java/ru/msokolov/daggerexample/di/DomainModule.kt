package ru.msokolov.daggerexample.di

import ru.msokolov.daggerexample.data.repository.ExampleRepositoryImpl
import ru.msokolov.daggerexample.domain.ExampleRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: ExampleRepositoryImpl): ExampleRepository
}