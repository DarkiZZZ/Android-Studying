package ru.msokolov.cleanarcexample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.msokolov.cleanarcexample.data.repository.UserRepositoryImpl
import ru.msokolov.cleanarcexample.data.storage.UserStorage
import ru.msokolov.cleanarcexample.data.storage.sharedPrefs.SharedPrefUserStorageImpl
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

@Module
class DataModule {

    @Provides
    fun provideUserStorage(context: Context): UserStorage{
        return SharedPrefUserStorageImpl(context = context)
    }

    @Provides
    fun provideUserRepository(userStorage: UserStorage): UserRepository{
        return UserRepositoryImpl(userStorage = userStorage)
    }
}