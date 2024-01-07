package com.example.masterand.Data

import kotlinx.coroutines.flow.Flow

class OfflineProfileRepository(private val profileDao: ProfileDao) : ProfileRepository {
    override fun getAllProfilesStream(): List<Profile> = profileDao.getAllProfiles()

    override fun getProfileById(id: Long): Flow<Profile?> =  profileDao.getProfileById(id)

    override suspend fun insertProfile(profile: Profile): Long = profileDao.insert(profile)

    override suspend fun updateProfile(profile: Profile) = profileDao.update(profile)
}
