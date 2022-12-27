package ru.msokolov.roomapptest.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val name: String,
)