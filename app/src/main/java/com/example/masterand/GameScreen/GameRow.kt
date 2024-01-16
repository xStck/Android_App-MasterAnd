package com.example.masterand.GameScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameRow(
    choosenColors: List<Color>,
    feedbackColors: List<Color>,
    clickable: Boolean,
    onSelectColorClick: (Int) -> Unit,
    onCheckClick: () -> Unit
) {
    var size = remember { mutableStateOf(0.dp) }
    val animatedSize = animateDpAsState(targetValue = size.value,
        animationSpec = tween(
            durationMillis = 1500,
        ),
        label = "animation"
    )

    LaunchedEffect(true) {
        size.value = 50.dp
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        SelectableColorsRow(colors = choosenColors, onClick = onSelectColorClick)
        Box(
            modifier = Modifier.size(50.dp),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = {
                    size.value = 0.dp
                    onCheckClick()

                },
                modifier = Modifier
                    .then(Modifier.size(animatedSize.value))
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background)
                    .animateContentSize(),

                colors = IconButtonDefaults.filledIconButtonColors(),
                enabled = clickable
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirm",
                    tint = Color.White,
                )
            }
        }
        FeedbackCircles(colors = feedbackColors)
    }
}