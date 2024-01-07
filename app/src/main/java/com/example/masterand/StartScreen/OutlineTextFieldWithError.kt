package com.example.masterand.StartScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithError(
    value: String,
    focusState: MutableState<Boolean>,
    isValid: Boolean,
    labelText: String,
    errorText: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (!focusState.value) {
                    focusState.value = it.isFocused
                }
            },
        label = { Text(labelText) },
        singleLine = true,
        isError = !isValid && focusState.value,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            if (!isValid && focusState.value) {
                Icon(Icons.Default.Info, contentDescription = "Error Icon")
            }
        },
        supportingText = {
            if (!isValid && focusState.value) {
                Text(errorText)
            }
        }
    )
}