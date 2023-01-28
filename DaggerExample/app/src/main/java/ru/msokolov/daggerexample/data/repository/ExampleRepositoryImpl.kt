package ru.msokolov.daggerexample.data.repository

import ru.msokolov.daggerexample.data.datasource.ExampleLocalDataSource
import ru.msokolov.daggerexample.data.datasource.ExampleRemoteDataSource
import ru.msokolov.daggerexample.data.mapper.ExampleMapper
import ru.msokolov.daggerexample.di.TestQualifier
import ru.msokolov.daggerexample.domain.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val localDataSource: ExampleLocalDataSource,
    @TestQualifier private val remoteDataSource: ExampleRemoteDataSource,
    private val mapper: ExampleMapper
) : ExampleRepository {

    override fun method() {
        localDataSource.method()
        remoteDataSource.method()
        mapper.map()
    }
}
