package com.example.masterand

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.masterand.ViewModels.PlayerScoreViewModel
import com.example.masterand.ViewModels.ProfileViewModel
import com.example.masterand.ViewModels.ScoreViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.profileRepository)
        }
        initializer {
            PlayerScoreViewModel(masterAndApplication().container.playerWithScoreRepository)
        }
        initializer {
            ScoreViewModel(masterAndApplication().container.scoreRepository)
        }
    }
}

fun CreationExtras.masterAndApplication(): MasterAndApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MasterAndApplication)
