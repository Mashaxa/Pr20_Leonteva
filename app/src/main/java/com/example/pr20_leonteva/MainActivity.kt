package com.example.pr20_leonteva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pr20_leonteva.ui.theme.Pr20_LeontevaTheme // ВАЖНЫЙ ИМПОРТ ВАШЕЙ ТЕМЫ

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pr20_LeontevaTheme { // ЗДЕСЬ ТЕПЕРЬ ИСПОЛЬЗУЕТСЯ ВАША ТЕМА
                Surface(color = MaterialTheme.colorScheme.background) {
                    NoteApp()
                }
            }
        }
    }
}

@Composable
fun NoteApp(viewModel: FirebaseViewModel = viewModel()) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Введите текст") },
                singleLine = true
            )
            Button(
                onClick = {
                    viewModel.addNote(inputText)
                    inputText = ""
                }
            ) {
                Text("SAVE")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = note.text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}