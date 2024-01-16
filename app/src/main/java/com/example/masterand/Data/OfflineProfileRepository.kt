package com.example.masterand.Data

import kotlinx.coroutines.flow.Flow

class OfflineProfileRepository(private val profileDao: ProfileDao) : ProfileRepository {
    override fun getAllProfilesStream(): Flow<List<Profile>> = profileDao.getAllProfilesStream()

    override suspend fun getProfileById(id: Long): Profile =  profileDao.getProfileById(id)

    override suspend fun insertProfile(profile: Profile): Long = profileDao.insert(profile)

    override suspend fun updateProfile(profile: Profile) = profileDao.update(profile)
    override suspend fun getProfileByEmail(email: String): Profile = profileDao.getProfileByEmail(email)
}
