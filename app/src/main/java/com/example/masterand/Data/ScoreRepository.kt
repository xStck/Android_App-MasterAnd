package com.example.masterand.Data

interface ScoreRepository {
    suspend fun insertScore(score: Score) : Long
}