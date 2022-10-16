package com.example.navigationtabsapp.model.accounts.entities

import com.example.navigationtabsapp.model.EmptyFieldException
import com.example.navigationtabsapp.model.Field
import com.example.navigationtabsapp.model.PasswordMismatchException

data class SingUpData(
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate(){
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (password != repeatPassword) throw PasswordMismatchException()

    }
}