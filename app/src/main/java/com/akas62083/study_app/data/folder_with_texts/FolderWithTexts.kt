package com.akas62083.study_app.data.folder_with_texts

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity

data class FolderWithTexts(
    @Embedded val folder: FolderEntity,
    @Relation(
        entity = TextEntity::class,
        parentColumn = "folder_id",
        entityColumn = "folder_id"
    )
    val texts: List<TextEntity>
)