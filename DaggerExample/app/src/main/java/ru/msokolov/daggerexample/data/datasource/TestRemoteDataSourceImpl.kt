package ru.msokolov.daggerexample.data.datasource

import android.util.Log
import javax.inject.Inject

class TestRemoteDataSourceImpl @Inject constructor()
    : ExampleRemoteDataSource {

    override fun method() {
        Log.d(TAG, "$this")
    }

    companion object{
        private const val TAG = "TestTAG"
    }
}