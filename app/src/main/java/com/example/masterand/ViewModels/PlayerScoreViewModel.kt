package com.example.masterand.ViewModels

import androidx.lifecycle.ViewModel
import com.example.masterand.Data.PlayerWithScore
import com.example.masterand.Data.PlayerWithScoreRepository
import kotlinx.coroutines.flow.Flow

class PlayerScoreViewModel(private val playerWithScoreRepository: PlayerWithScoreRepository) :
    ViewModel() {
    fun getAllPlayersWithScores(): Flow<List<PlayerWithScore>> {
        val profiles = playerWithScoreRepository.getPlayerWithScores()
        return profiles
    }
}