package com.ngaid.mistakesdiary.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "mistakes", indices = [Index("epochDay")])
data class Mistake(
    val description: String,
    val type: Int,
    val correction: String,
    val experience: String,
    val epochDay: Long,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

data class ShortMistake(
    val id: Int,
    val description: String,
    val type: Int
)