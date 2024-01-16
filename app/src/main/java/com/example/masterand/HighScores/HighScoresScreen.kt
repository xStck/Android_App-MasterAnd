package com.example.masterand.HighScores

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.example.masterand.Data.Profile
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.ProfileViewModel

@Composable
fun HighScoresScreen(
    navController: NavController,
    profileId: String,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val profilesState = viewModel.getAllProfiles().collectAsState(initial = emptyList())
    val profiles = remember { mutableStateListOf<Profile>() }
    profiles.clear()
    profiles.addAll(profilesState.value)

    LaunchedEffect(profileId != null && profileId.trim() != "") {
        if (profileId != null && profileId.trim() != "") {
            viewModel.getProfileById(profileId.toLong())
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Profile.route}/${profileId}")
                },
                shape = CircleShape,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
        profiles.forEach { profile ->
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
                    Text(
                        text = "Nazwa użytkownika: " + profile.name,
                        style = TextStyle(fontSize = 24.sp)
                    )
                    Text(
                        text = "Liczba kolorów: " + profile.numberOfColors,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Text(
                        text = "Najwyższy wynik: " + profile.score,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }


}