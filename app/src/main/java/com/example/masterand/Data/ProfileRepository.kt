package com.example.masterand.Data

import kotlinx.coroutines.flow.Flow

interface  ProfileRepository {
    /**
     * Retrieve all the Profiles from the the given data source.
     */
    fun getAllProfilesStream(): Flow<List<Profile>>

    /**
     * Retrieve an Profile from the given data source that matches with the [id].
     */
    suspend fun getProfileById(id: Long): Profile

    /**
     * Insert Profile in the data source
     */
    suspend fun insertProfile(profile: Profile) : Long

    /**
     * Update Profile in the data source
     */
    suspend fun updateProfile(profile: Profile)

    /**
     * Get profiles with specific email
     */
    suspend fun getProfileByEmail(email: String) : Profile

}