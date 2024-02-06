package com.example.weatherincuties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, onSubmit:(String) -> Unit){ //функция для поиска города; onSubmit - функция чтобы вернуть название города в мейнАктивити
    val dialogText = remember { // состояние для текста, чтобы можно было его менять
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { // onDismissRequest - нажатие вне экрана AlertDialog
            dialogState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(dialogText.value)
                dialogState.value = false
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Enter the name of the city:")
                TextField(value = dialogText.value, onValueChange = {
                    dialogText.value = it //it - текст который мы написали
                })
            }

        }
    )

}