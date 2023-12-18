package com.example.masterand.Navigation

import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstapp.Profile
import com.example.firstapp.ProfileCard
import com.example.masterand.GameScreen.GameScreen
import com.example.masterand.StartScreen.ProfileScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    var loginName: String
    var profileImageUri: Uri?
    var numberOfColors: String

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        composable(route = Screen.Start.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.Profile.route + "/{loginName}/{numberOfColors}/{profileImageUri}") { backStackEntry ->
            loginName = backStackEntry.arguments?.getString("loginName") ?: ""
            numberOfColors = backStackEntry.arguments?.getString("numberOfColors") ?: "5"
            val profileImageUriString = backStackEntry.arguments?.getString("profileImageUri")
            profileImageUri = profileImageUriString?.let { uriString ->
                val decodedUriBytes = Base64.decode(uriString, Base64.DEFAULT)
                Uri.parse(String(decodedUriBytes))
            }
            ProfileCard(
                profile = Profile(
                    loginName,
                    "Opis gracza 1\nOpis gracza 1\nOpis gracza 1",
                    profileImageUri
                ),
                navController = navController,
                numberOfColors = numberOfColors.toInt()
            )
        }

        composable(route = Screen.Game.route + "/{numberOfColors}") { backStackEntry ->
            numberOfColors = backStackEntry.arguments?.getString("numberOfColors") ?: "5"
            GameScreen(navController = navController, numberOfColors = numberOfColors.toInt())
        }
    }
}