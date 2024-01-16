package com.example.masterand.GameScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

data class GameRowData(
    var chosenColors: MutableList<Color> = mutableStateListOf(
        Color.White,
        Color.White,
        Color.White,
        Color.White
    ),
    var feedbackColors: MutableList<Color> = mutableStateListOf(
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

    val coroutineScope = rememberCoroutineScope()

    var attempts = remember { mutableStateOf(0) }
    var gameOver = remember {
        mutableStateOf(false)
    }
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
    var availableColors by remember { mutableStateOf(colors.shuffled().take(viewModel.numberOfColors.value)) }
    var correctColors by remember { mutableStateOf(selectRandomColors(availableColors)) }
    var gameRows  = remember { mutableStateListOf(GameRowData()) }

    LaunchedEffect(profileId) {
        if (profileId != null && profileId.trim().isNotEmpty()) {
            viewModel.getProfileById(profileId.toLong())
            availableColors = colors.shuffled().take(viewModel.numberOfColors.value)
            correctColors = selectRandomColors(availableColors)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Profile.route}/${viewModel.profileId.value}")
                },
                shape = CircleShape,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }

        Text(text = "Your score: ${attempts.value}", fontSize = 42.sp)
        gameRows.forEachIndexed { index, rowData ->
            var rowVisible by remember { mutableStateOf(false) }
            LaunchedEffect(rowVisible) {
                rowVisible = true
            }
            rowVisible = true
            AnimatedVisibility(
                visible = rowVisible,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                GameRow(
                    choosenColors = rowData.chosenColors,
                    feedbackColors = rowData.feedbackColors,
                    clickable = index == gameRows.lastIndex && !gameOver.value,
                    onSelectColorClick = { buttonIndex ->
                        if (index == gameRows.lastIndex && !gameOver.value) {
                            val newColor = selectNextAvailableColor(
                                availableColors,
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
                                correctColors,
                                Color.White
                            )
                            rowData.feedbackColors = newFeedbackColors
                            attempts.value++
                            if (rowData.feedbackColors.all { it == Color.Red }) {
                                gameOver.value = true
                            } else {
                                gameRows.add(GameRowData())
                                rowVisible = true // Ustawienie widoczności dla nowego wiersza
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (gameOver.value) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Score.route}/${viewModel.profileId.value}/${attempts.value}")
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
                coroutineScope.launch {
                    if(gameOver.value && attempts.value > viewModel.score.value){
                        viewModel.score.value = attempts.value
                        viewModel.updateScore()
                    }
                    navController.navigate(route = Screen.Start.route)
                }
            },
            shape = CircleShape
        ) {
            Text(text = "Logout")
        }
    }
}