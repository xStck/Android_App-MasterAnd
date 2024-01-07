package com.example.firstapp

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.ProfileViewModel

data class Profile(val login: String, val description: String, val profileImageUri: Uri?)

@Composable
fun ProfileCard(
    profileId: String,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    viewModel.getProfileById(profileId.toLong())
    val profileDetails = viewModel.profileUiState.profileDetails
    val allProfiles = viewModel.getAllProfilesInsteadLoggedUser(profileId.toLong())

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top

        ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Start.route}")
                },
                shape = CircleShape
            ) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            Column {
                AsyncImage(
                    model = profileDetails.profileImageUri,
                    contentDescription = "Profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(120.dp),
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Nazwa użytkownika: " + profileDetails.name, style = TextStyle(fontSize = 24.sp))
                Text(text = "Email: "+profileDetails.email, style = TextStyle(fontSize = 16.sp))
                Text(text = "Liczba kolorów: "+profileDetails.numberOfColors, style = TextStyle(fontSize = 16.sp))
                Button(
                    onClick = {
                        navController.navigate("${Screen.Game.route}/${profileDetails.id}")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Play")
                }
            }
        }

        for (profile in allProfiles){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        AsyncImage(
                            model = profile.profileImageUri,
                            contentDescription = "Profile image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(120.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = profile.name, style = TextStyle(fontSize = 24.sp))
                        Text(text = "Email: "+profile.email, style = TextStyle(fontSize = 16.sp))
                        Text(text = "Liczba kolorów: "+profile.numberOfColors, style = TextStyle(fontSize = 16.sp))
                        Text(text = "Wynik: "+profile.score, style = TextStyle(fontSize = 16.sp))
                    }
                }
        }
    }
}

