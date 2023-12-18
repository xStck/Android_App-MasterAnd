package com.example.firstapp

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.masterand.Navigation.Screen
import com.example.masterand.R

data class Profile(val login: String, val description: String, val profileImageUri: Uri?)

@Composable
fun ProfileCard(profile: Profile, navController: NavController, numberOfColors: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    navController.navigate(route = Screen.Start.route)
                },
                shape = CircleShape
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
                Text(text = profile.login, style = TextStyle(fontSize = 24.sp))
                Text(text = profile.description, style = TextStyle(fontSize = 16.sp))
                Button(
                    onClick = {
                        println("Button clicked!")
                        navController.navigate("${Screen.Game.route}/${numberOfColors}")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Play")
                }
            }
        }
    }
}

