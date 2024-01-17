package com.example.masterand.Data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerScoreDao {
    @Transaction
    @Query("SELECT * FROM profiles")
    fun getUsersWithPlaylists(): Flow<List<PlayerWithScore>>
}