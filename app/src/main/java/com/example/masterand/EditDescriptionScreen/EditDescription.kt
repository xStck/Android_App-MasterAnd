package com.example.masterand.EditDescriptionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.StartScreen.OutlinedTextFieldWithError
import com.example.masterand.ViewModels.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun EditDescription(
    profileId: String,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(true) {
        viewModel.getProfileById(profileId.toLong())
    }
    val coroutineScope = rememberCoroutineScope()

    val descriptionFocusState = remember { mutableStateOf(false) }
    val isDescriptionValid = viewModel.description.value.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            OutlinedTextFieldWithError(
                viewModel.description.value,
                descriptionFocusState,
                isDescriptionValid,
                "Enter description",
                "Description can't be empty",
                KeyboardType.Text,
                onValueChange = { viewModel.description.value = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateProfile()
                        navController.navigate("${Screen.Profile.route}/${viewModel.profileId.value}")
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                enabled = isDescriptionValid
            ) {
                Text(text = "Edytuj opis")
            }

        }
    }
}
