package com.deadely.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deadely.dictionary.dataclass.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        const val DATABASE_NAME: String = "dictionary_db"
    }
}
