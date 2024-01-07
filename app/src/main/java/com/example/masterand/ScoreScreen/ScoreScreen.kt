package com.example.masterand.ScoreScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ScoreScreen(navController: NavController,
               profileId: String,
                score: String,
               viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    viewModel.getProfileById(profileId.toLong())
    val profileDetails = viewModel.profileUiState.profileDetails
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Results",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 60.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        Text(
            text = "Recent score: " + score,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 25.dp)
        )
        Button(
            onClick = {
                navController.navigate("${Screen.Game.route}/${profileDetails.id}")
            },
            shape = CircleShape
        ) {
            Text(text = "Restart game")
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.updateProfileScore(score = score.toInt())
                    navController.navigate(route = Screen.Start.route)
                }
            },
            shape = CircleShape
        ) {
            Text(text = "Logout")
        }
    }
}