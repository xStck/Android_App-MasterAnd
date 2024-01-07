package com.example.masterand.Data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val profileRepository: ProfileRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val profileRepository: ProfileRepository by lazy {
        OfflineProfileRepository(MasterAndDatabase.getDatabase(context).profileDao())
    }
}
