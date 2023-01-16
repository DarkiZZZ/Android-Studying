package ru.msokolov.messengerfirebase

data class User(
    val id: String,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var isOnline: Boolean
)
