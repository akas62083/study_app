package com.akas62083.study_app.data.text

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TextDao {
    @Insert
    suspend fun insertText(text: TextEntity): Long
    @Delete
    suspend fun deleteText(text: TextEntity)
    @Update
    suspend fun updateText(text: TextEntity)
    @Query("select * from text_table order by text_sort_id")
    fun getAllTexts(): Flow<List<TextEntity>>
    @Query("select * from text_table where text_id = :id")
    fun getTextById(id: Long): Flow<TextEntity>
}