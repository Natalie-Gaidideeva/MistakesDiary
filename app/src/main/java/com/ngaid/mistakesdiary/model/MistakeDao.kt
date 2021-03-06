package com.ngaid.mistakesdiary.model

import androidx.room.*

@Dao
interface MistakeDao {

    @Query("SELECT id, description, type FROM mistakes WHERE epochDay = :epochDay")
    suspend fun getByDate(epochDay: Long): List<ShortMistake>

    @Query("SELECT * FROM mistakes WHERE id = :id")
    suspend fun getById(id: Int): Mistake

    @Insert
    suspend fun insert(mistake: Mistake)

    @Update
    suspend fun update(mistake: Mistake)
}