package com.example.masterand.Data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerScoreDao {
//    //złączenie tabel i pobranie danych do klasy pośredniczącej
//    @Query(
//        "SELECT profiles.id AS playerId, scores.scoreId AS scoreId " +
//                "FROM profiles, scores WHERE profiles.id = scores.playerId"
//    )
//    fun loadPlayersWithScores(): Flow<List<PlayerWithScore>>

    @Transaction
    @Query("SELECT * FROM profiles")
    fun getUsersWithPlaylists(): Flow<List<PlayerWithScore>>
}