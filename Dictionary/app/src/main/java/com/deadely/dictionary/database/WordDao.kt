package com.deadely.dictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deadely.dictionary.dataclass.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM words_table")
    fun getAllWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllWord(words: List<Word>)
}
