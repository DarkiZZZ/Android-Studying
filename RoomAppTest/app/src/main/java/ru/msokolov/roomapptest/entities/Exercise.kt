package ru.msokolov.roomapptest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long,

    val name: String,
    val repetitions: Int,

    @ColumnInfo(name = "machine_name")
    val machineName: String,
    val liftedWeight: Int
)