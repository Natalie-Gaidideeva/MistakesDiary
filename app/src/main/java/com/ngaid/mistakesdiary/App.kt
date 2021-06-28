package com.ngaid.mistakesdiary

import android.app.Application
import androidx.room.Room
import com.ngaid.mistakesdiary.model.AppDB
import java.time.LocalDate
import java.util.*


class App : Application() {
    companion object {
        lateinit var c: App
            private set

        lateinit var db: AppDB
            private set

        var currentDateInEpoch: Long = 0L
    }

    override fun onCreate() {
        super.onCreate()
        c = this

        db = Room.databaseBuilder(this, AppDB::class.java, "database")
            .build()

        currentDateInEpoch = LocalDate.now().toEpochDay()
    }
}