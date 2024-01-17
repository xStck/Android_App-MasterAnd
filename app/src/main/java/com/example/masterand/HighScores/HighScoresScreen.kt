package com.example.masterand.HighScores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.PlayerScoreViewModel

@Composable
fun HighScoresScreen(
    navController: NavController,
    profileId: String,
    viewModel2: PlayerScoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val profilesState by viewModel2.getAllPlayersWithScores().collectAsState(initial = emptyList())
    var sortedProfiles by remember { mutableStateOf(emptyList<Pair<String, Int>>()) }

    LaunchedEffect(profilesState) {
        val profileList = profilesState
        sortedProfiles = profileList.flatMap { profileScores ->
            profileScores.scores.map { score ->
                Pair(profileScores.profile.name, score.score)
            }
        }.sortedBy { it.second }
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
        if (sortedProfiles.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),  // Dodano odstęp dla lepszego wyglądu
                verticalAlignment = Alignment.CenterVertically,  // Wyśrodkowanie wertykalne
                horizontalArrangement = Arrangement.Center  // Wyśrodkowanie poziome
            ) {
                Text(
                    "Brak wyników",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center  // Wyśrodkowanie tekstu
                )
            }
        } else {
            sortedProfiles.forEach { profileScore ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "Nazwa użytkownika: " + profileScore.first,
                            style = TextStyle(fontSize = 24.sp)
                        )
                        Text(
                            text = "Wynik: " + profileScore.second,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}


