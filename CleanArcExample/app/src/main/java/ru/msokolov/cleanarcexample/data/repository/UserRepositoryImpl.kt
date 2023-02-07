package ru.msokolov.cleanarcexample.data.repository

import android.content.Context
import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.models.UserName
import ru.msokolov.cleanarcexample.domain.repository.UserRepository

class UserRepositoryImpl(context: Context) : UserRepository {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveName(saveParam: SaveUserNameParam): Boolean {
        sharedPreferences.edit().putString(KEY_FIRST_NAME, saveParam.name).apply()
        return true
    }

    override fun getName(): UserName {
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
        return UserName(firstName = firstName, lastName = lastName)
    }


    companion object {
        private const val SHARED_PREFS_NAME = "shared_prefs_name"
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val DEFAULT_NAME = "Default name"
    }
}