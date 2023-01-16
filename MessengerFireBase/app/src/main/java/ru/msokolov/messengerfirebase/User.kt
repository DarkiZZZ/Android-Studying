package ru.msokolov.messengerfirebase

data class User(
    var id: String,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var isOnline: Boolean
){
    public constructor() : this(
        "",
        "",
        "",
        -1,
        false
    )
}

