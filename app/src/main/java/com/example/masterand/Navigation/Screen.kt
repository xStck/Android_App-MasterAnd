package com.example.masterand.Navigation

sealed class Screen(val route: String) {
    object Start : Screen(route = "start")
    object Profile : Screen(route = "profile")
    object Game : Screen(route = "game")
}