package com.ngaid.mistakesdiary.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Mistake::class])
abstract class AppDB: RoomDatabase() {
    abstract fun mistakeDao(): MistakeDao
}