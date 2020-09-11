package com.example.client.database

import androidx.room.*

@Dao
interface RepoDao {
    @Query("SELECT * FROM favorites_table")
    suspend fun getAllRepos(): List<RepoDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(photo: RepoDBEntity)

    @Delete
    suspend fun deleteRepo(photo: RepoDBEntity)
}