package com.example.prak5tugas.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringArrayResource
import kotlinx.coroutines.delay

@Composable
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colorResource(R.color.form_background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.form_padding)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.form_spacing)
            )
        ) {
            Text(
                text = stringResource(R.string.form_title),
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(R.color.form_text)
            )

            Text(
                text = stringResource(R.string.form_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.form_secondary_text)
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    showResult = false
                },
                label = { Text(stringResource(R.string.first_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    showResult = false
                },
                label = { Text(stringResource(R.string.last_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showResult = false
                },
                label = { Text(stringResource(R.string.email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                    showResult = false
                },
                label = { Text(stringResource(R.string.birth_date_label)) },
                placeholder = {
                    Text(stringResource(R.string.birth_date_hint))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = stringResource(R.string.experience_label),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.form_text)
            )

            experienceOptions.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = experience == option,
                        onClick = {
                            experience = option
                            showResult = false
                        }
                    )
                    Text(
                        text = option,
                        color = colorResource(R.color.form_text)
                    )
                }
            }

            Text(
                text = stringResource(R.string.skills_label),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.form_text)
            )

            skillOptions.forEach { skill ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = skill in selectedSkills,
                        onCheckedChange = { checked ->
                            if (checked) {
                                if (skill !in selectedSkills) {
                                    selectedSkills.add(skill)
                                }
                            } else {
                                selectedSkills.remove(skill)
                            }
                            showResult = false
                        }
                    )
                    Text(
                        text = skill,
                        color = colorResource(R.color.form_text)
                    )
                }
            }

}