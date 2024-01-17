package com.example.masterand.StartScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.masterand.R

@Composable
fun ProfileImageWithPicker(profileImageUri: Uri?, selectImageOnClick: () -> Unit) {
    AsyncImage(
        model = if (profileImageUri == null) Uri.parse("android.resource://com.example.masterand/${R.drawable.baseline_question_mark_24}") else profileImageUri,
        contentDescription = "Profile image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
    IconButton(
        onClick = selectImageOnClick,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(4.dp)
    ) {
        Icon(Icons.Default.Refresh, contentDescription = "Select Image")
    }
}