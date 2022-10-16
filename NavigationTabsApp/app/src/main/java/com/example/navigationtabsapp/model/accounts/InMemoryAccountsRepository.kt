package com.example.navigationtabsapp.model.accounts

import com.example.navigationtabsapp.model.AccountAlreadyExistsException
import com.example.navigationtabsapp.model.AuthException
import com.example.navigationtabsapp.model.EmptyFieldException
import com.example.navigationtabsapp.model.Field
import com.example.navigationtabsapp.model.accounts.entities.Account
import com.example.navigationtabsapp.model.accounts.entities.SingUpData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryAccountsRepository: AccountsRepository {

    private var currentAccountFlow = MutableStateFlow<Account?>(null)

    private val accounts = mutableListOf(
        AccountRecord(
            account = Account(
                username = "admin",
                email = "admin@gmail.com"
            ),
            password = "123"
        )
    )

    init {
        currentAccountFlow.value = accounts[0].account
    }

    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return currentAccountFlow.value != null
    }

    override suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw  EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        delay(1000)

        val record = getRecordByEmail(email)
        if (record != null && record.password == password){
            currentAccountFlow.value = record.account
        }
        else{
            throw AuthException()
        }
    }

    override suspend fun signUp(signUpData: SingUpData) {
        signUpData.validate()

        delay(1000)
        val accountRecord = getRecordByEmail(signUpData.email)
        if (accountRecord != null) throw AccountAlreadyExistsException()

        val newAccount = Account(
            username = signUpData.username,
            email = signUpData.email,
            createdAt = System.currentTimeMillis()
        )
    }

    override fun logout() {
        currentAccountFlow.value = null
    }

    override fun getAccount(): Flow<Account?> = currentAccountFlow

    override suspend fun updateAccountUsername(newUsername: String) {
        TODO("Not yet implemented")
    }

    private fun getRecordByEmail(email: String) = accounts.firstOrNull { it.account.email == email }

    private class AccountRecord(
        var account: Account,
        val password: String
    )
}