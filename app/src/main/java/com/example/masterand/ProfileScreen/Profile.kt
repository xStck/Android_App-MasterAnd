package com.example.masterand.ProfileScreen

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

@Composable
fun ProfileCard(
    profileId: String,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(true) {
        viewModel.getProfileById(profileId.toLong())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top

        ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    navController.navigate("${Screen.Start.route}?profileId=${profileId}")
                },
                shape = CircleShape,
                modifier = Modifier.padding(8.dp)
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
                    model = viewModel.profileImageUri.value,
                    contentDescription = "Profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(120.dp),
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Nazwa użytkownika: " + viewModel.name.value, style = TextStyle(fontSize = 24.sp))
                Text(text = "Email: "+viewModel.email.value, style = TextStyle(fontSize = 16.sp))
                Text(text = "Liczba kolorów: "+viewModel.numberOfColors.value, style = TextStyle(fontSize = 16.sp))
                Text(text = "Najwyższy wynik: "+viewModel.score.value, style = TextStyle(fontSize = 16.sp))
                Text(text = "Opis: "+viewModel.description.value, style = TextStyle(fontSize = 16.sp))
                Row{
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Game.route}/${viewModel.profileId.value}")
                        },
                        modifier = Modifier
                            .padding(8.dp).width(100.dp)
                    ) {
                        Text(text = "Play")
                    }
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Description.route}/${viewModel.profileId.value}")
                        },
                        modifier = Modifier
                            .padding(8.dp).width(140.dp)
                    ) {
                        Text(text = "Edytuj opis")
                    }
                }
                Row{
                    Button(
                        onClick = {
                            navController.navigate("${Screen.HighScores.route}/${viewModel.profileId.value}")
                        },
                        modifier = Modifier
                            .padding(8.dp).width(130.dp)
                    ) {
                        Text(text = "High scores")
                    }
                    Button(
                        onClick = {
                                navController.navigate(route = Screen.Start.route)
                        },
                        modifier = Modifier.padding(8.dp).width(100.dp),
                        shape = CircleShape
                    ) {
                        Text(text = "Logout")
                    }
                }

            }
        }
    }
}

