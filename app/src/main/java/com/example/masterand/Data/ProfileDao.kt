package com.example.masterand.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * from profiles ORDER BY score DESC")
    fun getAllProfilesStream(): Flow<List<Profile>>

    @Query("SELECT * from profiles WHERE id = :id")
    suspend fun getProfileById(id: Long): Profile

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile) : Long

    @Query("SELECT * from profiles WHERE email = :email")
    suspend fun getProfileByEmail(email: String): Profile

    @Update
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}