package com.akas62083.study_app.data.folder_with_texts

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderWithTextsDao {
    @Transaction
    @Query("SELECT * FROM folder_table order by folder_sort_id desc")
    fun getAllParentsWithChildren(): Flow<List<FolderWithTexts>>
}