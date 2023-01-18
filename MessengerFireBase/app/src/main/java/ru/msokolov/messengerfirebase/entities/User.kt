package ru.msokolov.messengerfirebase.entities

import java.io.Serializable


data class User(
    var id: String,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var online: Boolean
): Serializable{
    public constructor() : this(
        "",
        "",
        "",
        -1,
        false
    )
}

