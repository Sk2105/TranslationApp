package com.sgtech.translationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgtech.translationapp.ui.theme.TranslationAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslationAppTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TranslationApp()
                    }

                    // Add other content here

                }
            }
        }
    }
}

@Composable
fun TranslationApp() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Translation Screen
        TranslationScreen()
        // Other content
    }


}

@Composable
fun TranslationScreen() {

    // Translation screen content
    val sourceText = remember { mutableStateOf("") }
    val translatedText = remember { mutableStateOf("") }
    var sourceLanguageMenuExpanded by remember { mutableStateOf(false) }
    var targetLanguageMenuExpanded by remember { mutableStateOf(false) }
    var selectedSourceLanguage by remember { mutableStateOf("English") }
    var selectedTargetLanguage by remember { mutableStateOf("Hindi") }

    val languages = listOf(
        "English",
        "Hindi",
        "Arabic",
        "Bengali",
        "Gujarati",
        "Kannada",
        "Malayalam",
        "Marathi",
        "Punjabi",
        "Tamil",
        "Telugu",
        "Urdu"
    )

    val languageCodeMap = mapOf(
        "English" to "en",
        "Hindi" to "hi",
        "Arabic" to "ar",
        "Bengali" to "bn",
        "Gujarati" to "gu",
        "Kannada" to "kn",
        "Malayalam" to "ml",
        "Marathi" to "mr",
        "Punjabi" to "pa",
        "Tamil" to "ta",
        "Telugu" to "te",
        "Urdu" to "ur"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Translation App", fontSize = 24.sp)
        // Other content
        // Add other content here

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Button(onClick = {
                    sourceLanguageMenuExpanded = true
                }) {
                    Text(text = selectedSourceLanguage)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
                DropdownMenu(
                    expanded = sourceLanguageMenuExpanded,
                    onDismissRequest = { sourceLanguageMenuExpanded = false }) {

                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(text = language) },
                            onClick = {
                                selectedSourceLanguage = language
                                sourceLanguageMenuExpanded = false
                                // Handle language selection
                            }
                        )
                    }


                }
            }


            Text(text = "To")

            Column {
                Button(onClick = {
                    targetLanguageMenuExpanded = true
                }) {
                    Text(text = selectedTargetLanguage)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }

                DropdownMenu(
                    expanded = targetLanguageMenuExpanded,
                    onDismissRequest = { targetLanguageMenuExpanded = false }) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(text = language) },
                            onClick = {
                                selectedTargetLanguage = language
                                targetLanguageMenuExpanded = false
                                // Handle language selection
                            }
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            textStyle = TextStyle(fontSize = 20.sp),
            value = sourceText.value,
            onValueChange = {
                sourceText.value = it
            },
            label = { Text("Enter Text Here", fontSize = 20.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(5.dp)
        )

        val scope = rememberCoroutineScope()
        Button(
            onClick = {

                val sourceValue = sourceText.value
                // Perform translation logic here
                scope.launch(Dispatchers.IO) {
                    // Perform translation logic here
                    val translationParameters = TranslationParameters(
                        sourceLanguage = languageCodeMap[selectedSourceLanguage]!!,
                        targetLanguage = languageCodeMap[selectedTargetLanguage]!!,
                        text = sourceValue
                    )
                    translatedText.value = Translation.translate(translationParameters)

                }

            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Translate")
        }

        Text(
            text = "Result :-",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )
        Text(
            text = translatedText.value,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            fontSize = 24.sp
        )


    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TranslationAppTheme {
        TranslationApp()
    }
}