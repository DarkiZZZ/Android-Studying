package ru.msokolov.messengerfirebase.entities

data class Message(
    var text: String,
    var senderId: String,
    var receiverId: String
){
    public constructor(): this(
        "",
        "",
        ""
    )
}
