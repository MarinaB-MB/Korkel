package com.example.client.di

import android.content.Context
import androidx.room.Room
import com.example.client.database.AppDataBase
import com.example.client.database.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideRepoDao(appDataBase: AppDataBase): RepoDao {
        return appDataBase.repoDao()
    }
}
