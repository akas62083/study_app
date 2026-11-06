package com.akas62083.study_app.di

import android.content.Context
import androidx.room.Room
import com.akas62083.study_app.data.AppDatabase
import com.akas62083.study_app.data.folder.FolderDao
import com.akas62083.study_app.data.folder_with_texts.FolderWithTextsDao
import com.akas62083.study_app.data.text.TextDao
import com.akas62083.study_app.repository.data.DataRepository
import com.akas62083.study_app.repository.data.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    @Provides
    fun provideFolderDao(database: AppDatabase): FolderDao = database.getFolderDao()
    @Provides
    fun provideTextDao(database: AppDatabase): TextDao = database.getTextDao()
    @Provides
    fun provideFolderWithTextsDao(database: AppDatabase): FolderWithTextsDao = database.getFolderWithTextsDao()


}