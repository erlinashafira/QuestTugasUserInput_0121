package com.example.prak5tugas.ui.theme

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringArrayResource
import kotlinx.coroutines.delay

fun Biodata(modifier: Modifier = Modifier) {
    val experienceOptions = stringArrayResource(R.array.experience_options)
    val skillOptions = stringArrayResource(R.array.skill_options)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    val selectedSkills = remember { mutableStateListOf<String>() }
    var showResult by remember { mutableStateOf(false) }

    val formComplete =
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                email.isNotBlank() &&
                android.util.Patterns.EMAIL_ADDRESS
                    .matcher(email.trim()).matches() &&
                birthDate.isNotBlank() &&
                experience.isNotBlank() &&
                selectedSkills.isNotEmpty()

    LaunchedEffect(showResult) {
        if (showResult) {
            delay(
                integerResource(R.integer.result_display_duration).toLong()
            )
            showResult = false
        }
    }
}