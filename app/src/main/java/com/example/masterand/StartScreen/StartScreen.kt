package com.example.masterand.StartScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.masterand.AppViewModelProvider
import com.example.masterand.Navigation.Screen
import com.example.masterand.ViewModels.ProfileViewModel
import kotlinx.coroutines.launch

private fun createPickImageIntent(): Intent {
    return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
        type = "image/*"
    }
}

@Composable
fun StartScreen(
    profileId: String?,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(profileId != null && profileId.trim() != ""){
        if (profileId != null && profileId.trim() != "") {
            viewModel.getProfileById(profileId.toLong())
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val nameFocusState = remember { mutableStateOf(false) }
    val emailFocusState = remember { mutableStateOf(false) }
    val colorsFocusState = remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        label="scale",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val isNameValid = viewModel.name.value.isNotEmpty()
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(viewModel.email.value).matches()
    val isColorsValid = viewModel.numberOfColors.value in 5..10
    val dataValid:Boolean = isNameValid && isEmailValid && isColorsValid

    val selectImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val selectedImageUri: Uri? = data?.data
                viewModel.profileImageUri.value = selectedImageUri
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 48.dp).graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                transformOrigin = TransformOrigin.Center
            ))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)
                .clip(CircleShape)
                .clickable { selectImageLauncher.launch(createPickImageIntent()) }
        ) {
            ProfileImageWithPicker(profileImageUri = viewModel.profileImageUri.value, selectImageOnClick = {
                selectImageLauncher.launch(createPickImageIntent())
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextFieldWithError(
            viewModel.name.value,
            nameFocusState,
            isNameValid,
            "Enter name",
            "Name can't be empty",
            KeyboardType.Text,
            onValueChange = { viewModel.name.value = it}
        )
        OutlinedTextFieldWithError(
            viewModel.email.value,
            emailFocusState,
            isEmailValid,
            "Enter e-mail",
            "It must be e-mail",
            KeyboardType.Email,
            onValueChange = { viewModel.email.value = it}

        )
        OutlinedTextFieldWithError(
            viewModel.numberOfColors.value.toString(),
            colorsFocusState,
            isColorsValid,
            "Enter number of colors",
            "The number must be between 5 and 10",
            KeyboardType.Number,
            onValueChange = {
                if(it == "" || it == null){
                    viewModel.numberOfColors.value = 0
                }else{
                    viewModel.numberOfColors.value = it.toInt()
                }
            }
            )

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.saveProfile()
                    navController.navigate("${Screen.Profile.route}/${viewModel.profileId.value}")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = dataValid
        ) {
            Text("Next")
        }
    }
}