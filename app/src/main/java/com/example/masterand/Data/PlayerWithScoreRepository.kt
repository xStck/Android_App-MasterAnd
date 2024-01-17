package com.example.masterand.Data

import kotlinx.coroutines.flow.Flow

interface PlayerWithScoreRepository {
    fun getPlayerWithScores(): Flow<List<PlayerWithScore>>

}