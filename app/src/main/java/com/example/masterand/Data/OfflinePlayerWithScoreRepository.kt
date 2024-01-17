package com.example.masterand.Data

import kotlinx.coroutines.flow.Flow

class OfflinePlayerWithScoreRepository(private val playerWithScoreDao: PlayerScoreDao) : PlayerWithScoreRepository {
    override fun getPlayerWithScores(): Flow<List<PlayerWithScore>> = playerWithScoreDao.getUsersWithPlaylists()
}