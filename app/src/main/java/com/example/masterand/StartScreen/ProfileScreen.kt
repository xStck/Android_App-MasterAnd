package com.example.masterand.StartScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.masterand.Navigation.Screen
import com.example.masterand.R
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private fun createPickImageIntent(): Intent {
    return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
        type = "image/*"
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    val nameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val colorsState = rememberSaveable { mutableStateOf("") }
    val profileImageUri =
        rememberSaveable { mutableStateOf<Uri?>(Uri.parse("android.resource://com.example.masterand/${R.drawable.baseline_question_mark_24}")) }

    val nameFocusState = remember { mutableStateOf(false) }
    val emailFocusState = remember { mutableStateOf(false) }
    val colorsFocusState = remember { mutableStateOf(false) }

    val isNameValid = nameState.value.isNotEmpty()
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches()
    val isColorsValid = colorsState.value.toIntOrNull() in 5..10

    val selectImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val selectedImageUri: Uri? = data?.data
                profileImageUri.value = selectedImageUri
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
            modifier = Modifier.padding(bottom = 48.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)
                .clip(CircleShape)
                .clickable { selectImageLauncher.launch(createPickImageIntent()) }
        ) {
            ProfileImageWithPicker(profileImageUri = profileImageUri.value, selectImageOnClick = {
                selectImageLauncher.launch(createPickImageIntent())
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextFieldWithError(
            nameState,
            nameFocusState,
            isNameValid,
            "Enter name",
            "Name can't be empty",
            KeyboardType.Text
        )
        OutlinedTextFieldWithError(
            emailState,
            emailFocusState,
            isEmailValid,
            "Enter e-mail",
            "It must be e-mail",
            KeyboardType.Email
        )
        OutlinedTextFieldWithError(
            colorsState,
            colorsFocusState,
            isColorsValid,
            "Enter number of colors",
            "The number must be between 5 and 10",
            KeyboardType.Number
        )

        Button(
            onClick = {
                val profileImageUriString = profileImageUri.value?.toString()?.let { uri ->
                    Base64.encodeToString(uri.toByteArray(), Base64.DEFAULT)
                }
                navController.navigate("${Screen.Profile.route}/${nameState.value}/${colorsState.value}/${profileImageUriString}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
