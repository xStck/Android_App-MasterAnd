package com.example.masterand.Navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.masterand.EditDescriptionScreen.EditDescription
import com.example.masterand.GameScreen.GameScreen
import com.example.masterand.HighScores.HighScoresScreen
import com.example.masterand.ProfileScreen.ProfileCard
import com.example.masterand.ScoreScreen.ScoreScreen
import com.example.masterand.StartScreen.StartScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        composable(route = Screen.Start.route+"?profileId={profileId}",
            arguments = listOf(navArgument("profileId"){defaultValue=""}),
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500, easing = EaseIn)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }
            ) {backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId")
            StartScreen(profileId = profileId, navController = navController)
        }

        composable(route = Screen.Profile.route + "/{profileId}",
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500, easing = EaseIn)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }) { backStackEntry ->
            ProfileCard(
                profileId = backStackEntry.arguments?.getString("profileId") ?: "",
                navController = navController
            )
        }

        composable(route = Screen.Description.route + "/{profileId}",
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500, easing = EaseIn)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }) { backStackEntry ->
            EditDescription(
                profileId = backStackEntry.arguments?.getString("profileId") ?: "",
                navController = navController
            )
        }

        composable(route = Screen.Game.route + "/{profileId}",
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500, easing = EaseIn)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }) { backStackEntry ->
            GameScreen(navController = navController, profileId = backStackEntry.arguments?.getString("profileId") ?: "")
        }

        composable(route = Screen.Score.route + "/{profileId}/{score}",            enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 500)) +
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(durationMillis = 500, easing = EaseIn)
                    )
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }) { backStackEntry ->
            ScoreScreen(navController = navController, profileId = backStackEntry.arguments?.getString("profileId") ?: "", score = backStackEntry.arguments?.getString("score")?: "0")
        }

        composable(route = Screen.HighScores.route + "/{profileId}",
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500, easing = EaseIn)
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500)) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(durationMillis = 500, easing = EaseOut)
                        )
            }) { backStackEntry ->
            HighScoresScreen(navController = navController, profileId = backStackEntry.arguments?.getString("profileId") ?: "")
        }
    }
}