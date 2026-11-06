package com.akas62083.study_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akas62083.study_app.data.folder.FolderDao
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTextsDao
import com.akas62083.study_app.data.text.TextDao
import com.akas62083.study_app.data.text.TextEntity

@Database(
    entities = [
        FolderEntity::class,
        TextEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getFolderDao(): FolderDao
    abstract fun getTextDao(): TextDao
    abstract fun getFolderWithTextsDao(): FolderWithTextsDao
}