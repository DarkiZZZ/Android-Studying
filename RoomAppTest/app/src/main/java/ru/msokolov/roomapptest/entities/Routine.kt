package ru.msokolov.roomapptest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@Entity
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val routineId: Long,

    @ColumnInfo(name = "due_day")
    val dueDay: Date,

    @TypeConverters(ListConverter::class)
)
