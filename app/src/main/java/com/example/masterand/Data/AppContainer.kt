package com.example.masterand.Data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val profileRepository: ProfileRepository
    val playerWithScoreRepository: PlayerWithScoreRepository
    val scoreRepository: ScoreRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val profileRepository: ProfileRepository by lazy {
        OfflineProfileRepository(MasterAndDatabase.getDatabase(context).profileDao())
    }

    override val playerWithScoreRepository: PlayerWithScoreRepository by lazy {
        OfflinePlayerWithScoreRepository(MasterAndDatabase.getDatabase(context).playerScoreDao())
    }

    override val scoreRepository: ScoreRepository by lazy {
        OfflineScoreRepository(MasterAndDatabase.getDatabase(context).scoreDao())
    }
}
