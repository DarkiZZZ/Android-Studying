package ru.msokolov.cleanarcexample.di

import org.koin.dsl.module
import ru.msokolov.cleanarcexample.data.repository.UserRepositoryImpl
import ru.msokolov.cleanarcexample.data.storage.UserStorage
import ru.msokolov.cleanarcexample.data.storage.sharedPrefs.SharedPrefUserStorageImpl
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

val dataModule = module {

    single<UserStorage> {
        SharedPrefUserStorageImpl(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }
}