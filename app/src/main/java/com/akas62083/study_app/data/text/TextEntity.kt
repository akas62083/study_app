package com.akas62083.study_app.data.text

import android.R.attr.value
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.domain.TextType

@Entity(
    tableName = "text_table",
    foreignKeys = [
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = ["folder_id"],
            childColumns = ["folder_id"],
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.RESTRICT
        )
    ],
)
data class TextEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "text_id")
    val id: Long = 0,
    @ColumnInfo(name = "folder_id")
    val folderId: Long,
    @ColumnInfo(name = "text_sort_id")
    val sortId: Long = id,
    @ColumnInfo(name = "text_name")
    val name: String = "",
    @ColumnInfo(name = "text_sentence")
    val sentence: String = "",
    @ColumnInfo(name = "text_is_working")
    val isWorking: Boolean = false,
    @ColumnInfo(name = "text_type")
    val textType: TextType = TextType.TEXT
)