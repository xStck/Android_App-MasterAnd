package com.example.masterand.GameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.ProfileViewModel

data class GameRowData(
    val chosenColors: MutableList<Color> = mutableStateListOf(
        Color.White,
        Color.White,
        Color.White,
        Color.White
    ),
    val feedbackColors: MutableList<Color> = mutableStateListOf(
        Color.White,
        Color.White,
        Color.White,
        Color.White
    )
)

@Composable
fun GameScreen(
    navController: NavController,
    profileId: String,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var attempts = remember { mutableStateOf(0) }
    var gameOver = remember {
        mutableStateOf(false)
    }
    viewModel.getProfileById(profileId.toLong())
    val profileDetails = viewModel.profileUiState.profileDetails
    val numberOfColors = profileDetails.numberOfColors.toInt()
    val colors =
        listOf(
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.Magenta,
            Color.Cyan,
            Color.LightGray,
            Color.Gray,
            Color.DarkGray,
            Color.Black
        )
    val availableColors = remember {
        mutableStateOf(colors.shuffled().take(numberOfColors))
    }
    var correctColors = remember {
        mutableStateOf(selectRandomColors(availableColors.value))
    }
    var gameRows = remember { mutableStateListOf(GameRowData()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Profile.route}/${profileDetails.id}")
                },
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }

        Text(text = "Your score: ${attempts.value}", fontSize = 42.sp)
        gameRows.forEachIndexed { index, rowData ->
            GameRow(
                choosenColors = rowData.chosenColors,
                feedbackColors = rowData.feedbackColors,
                clickable = index == gameRows.lastIndex && !gameOver.value,
                onSelectColorClick = { buttonIndex ->
                    if (index == gameRows.lastIndex && !gameOver.value) {
                        val newColor = selectNextAvailableColor(
                            availableColors.value,
                            rowData.chosenColors,
                            buttonIndex
                        )
                        rowData.chosenColors[buttonIndex] = newColor
                    }
                },
                onCheckClick = {
                    if (index == gameRows.lastIndex && !gameOver.value) {
                        val newFeedbackColors = checkColors(
                            rowData.chosenColors.toList(),
                            correctColors.value,
                            Color.White
                        )
                        rowData.feedbackColors.clear()
                        rowData.feedbackColors.addAll(newFeedbackColors)
                        attempts.value++
                        if (rowData.feedbackColors.all { it == Color.Red }) {
                            gameOver.value = true
                        } else {
                            gameRows.add(GameRowData())
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (gameOver.value) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Score.route}/${profileDetails.id}/${attempts.value}")
                },
                shape = CircleShape
            ) {
                Text(
                    text = "High score table",
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(route = Screen.Start.route)
            },
            shape = CircleShape
        ) {
            Text(text = "Logout")
        }
    }
}