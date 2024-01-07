package com.example.masterand.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstapp.ProfileCard
import com.example.masterand.GameScreen.GameScreen
import com.example.masterand.ScoreScreen.ScoreScreen
import com.example.masterand.StartScreen.StartScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        composable(route = Screen.Start.route) {backStackEntry ->
            StartScreen(navController = navController)
        }

        composable(route = Screen.Profile.route + "/{profileId}") { backStackEntry ->
            ProfileCard(
                profileId = backStackEntry.arguments?.getString("profileId") ?: "",
                navController = navController
            )
        }

        composable(route = Screen.Game.route + "/{profileId}") { backStackEntry ->
            GameScreen(navController = navController, profileId = backStackEntry.arguments?.getString("profileId") ?: "")
        }

        composable(route = Screen.Score.route + "/{profileId}/{score}") { backStackEntry ->
            ScoreScreen(navController = navController, profileId = backStackEntry.arguments?.getString("profileId") ?: "", score = backStackEntry.arguments?.getString("score")?: "0")
        }
    }
}