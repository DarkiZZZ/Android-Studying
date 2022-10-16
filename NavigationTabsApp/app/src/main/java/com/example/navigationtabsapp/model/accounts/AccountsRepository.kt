package com.example.navigationtabsapp.model.accounts

import com.example.navigationtabsapp.model.accounts.entities.Account
import com.example.navigationtabsapp.model.accounts.entities.SingUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun isSignedIn(): Boolean

    /*Tries to sign-in with email and password.
    If sign-in failed - throws exceptions*/

    suspend fun signIn(email: String, password: String)


    suspend fun signUp(signUpData: SingUpData)


    fun logout()


    fun getAccount(): Flow<Account?>


    suspend fun updateAccountUsername(newUsername: String)
}