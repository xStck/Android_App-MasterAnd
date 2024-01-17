package com.example.masterand.Data

class OfflineScoreRepository(private val scoreDao: ScoreDao) : ScoreRepository {
    override suspend fun insertScore(score: Score): Long = scoreDao.insert(score)
}