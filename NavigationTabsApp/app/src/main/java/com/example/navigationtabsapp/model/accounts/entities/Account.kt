package com.example.navigationtabsapp.model.accounts.entities

data class Account(
    val username: String,
    val email: String,
    val createdAt: Long = UNKNOWN_CREATED_AT
){
    companion object{
        const val UNKNOWN_CREATED_AT = 0L
    }
}