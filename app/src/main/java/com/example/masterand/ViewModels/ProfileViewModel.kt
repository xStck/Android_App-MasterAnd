package com.example.masterand.ViewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterand.Data.Profile
import com.example.masterand.Data.ProfileRepository
import com.example.masterand.R
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    var profileUiState by mutableStateOf(ProfileUiState())
        private set

    fun updateUiState(profileDetails: ProfileDetails) {
        profileUiState =
            ProfileUiState(
                profileDetails = profileDetails,
                isEntryValid = validateInput(profileDetails)
            )
    }

    fun getProfileById(id: Long) {
        viewModelScope.launch {
            profileRepository.getProfileById(id).collect { profile ->
                if (profile != null) {
                    profileUiState = profile.toProfileUiState(true)
                } else {
                    profileUiState = ProfileUiState()
                }
            }
        }
    }

    fun getAllProfilesInsteadLoggedUser(id: Long): MutableList<ProfileDetails> {
        var listOfProfilesDetails: MutableList<ProfileDetails> = mutableListOf()
        var listOfProfiles = profileRepository.getAllProfilesStream()

        if (!listOfProfiles.isEmpty()) {
            listOfProfiles.forEach { profile ->
                if(profile.id != id){
                    listOfProfilesDetails.add(profile.toProfileDetails())
                }
            }
        }
        return listOfProfilesDetails
    }

    suspend fun saveProfile(): Long {
        if (validateInput()) {
            val insertedId =
                profileRepository.insertProfile(profileUiState.profileDetails.toProfile())
            return insertedId
        }
        return 0
    }

    suspend fun updateProfileScore(score: Int) {
        profileUiState.profileDetails.score = score.toString()
        profileRepository.updateProfile(profileUiState.profileDetails.toProfile())
    }

    private fun validateInput(uiState: ProfileDetails = profileUiState.profileDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && email.isNotBlank() && numberOfColors.isNotBlank()
        }
    }

}

data class ProfileUiState(
    val profileDetails: ProfileDetails = ProfileDetails(),
    val isEntryValid: Boolean = false
)

data class ProfileDetails(
    var id: Long = 0,
    val email: String = "",
    val name: String = "",
    val numberOfColors: String = "",
    var score: String = "",
    val profileImageUri: Uri? = Uri.parse("android.resource://com.example.masterand/${R.drawable.baseline_question_mark_24}")
)

fun ProfileDetails.toProfile(): Profile = Profile(
    id = id,
    email = email,
    name = name,
    numberOfColors = numberOfColors.toIntOrNull() ?: 4,
    score = score.toIntOrNull() ?: 0,
    profileImageUri = profileImageUri
)

fun Profile.toProfileUiState(isEntryValid: Boolean = false): ProfileUiState = ProfileUiState(
    profileDetails = this.toProfileDetails(),
    isEntryValid = isEntryValid
)

fun Profile.toProfileDetails(): ProfileDetails = ProfileDetails(
    id = id,
    email = email,
    name = name,
    numberOfColors = numberOfColors.toString(),
    score = score.toString(),
    profileImageUri = profileImageUri
)
