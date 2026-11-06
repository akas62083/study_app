package com.akas62083.study_app.data.folder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "folder_id")
    val id: Long = 0,
    @ColumnInfo(name = "folder_sort_id")
    val sortId: Long? = id,
    @ColumnInfo(name = "folder_name")
    val name: String = "",
    @ColumnInfo(name = "folder_is_opened")
    val isOpened: Boolean = false,
    @ColumnInfo(name = "folder_is_red")
    val isRed: Boolean = false
)