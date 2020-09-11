package com.example.client.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepoDBEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {
        const val DATABASE_NAME: String = "github_db"
    }
}