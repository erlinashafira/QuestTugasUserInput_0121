package com.example.prak5tugas.ui.theme

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import com.example.prak5tugas.R

@Composable
fun Biodata(modifier: Modifier = Modifier) {
    val experienceOptions =
        stringArrayResource(R.array.experience_options)
    val skillOptions =
        stringArrayResource(R.array.skill_options)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    val selectedSkills = remember {
        mutableStateListOf<String>()
    }

    var showResult by remember { mutableStateOf(false) }

    val formComplete =
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() &&
                birthDate.isNotBlank() &&
                experience.isNotBlank() &&
                selectedSkills.isNotEmpty()

    val resultDisplayDuration =
        integerResource(R.integer.result_display_duration)

    LaunchedEffect(showResult) {
        if (showResult) {
            delay(resultDisplayDuration.toLong())
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

            Image(
                painter = painterResource(R.drawable.foto),
                contentDescription = stringResource(R.string.form_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.photo_height))
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
                label = {
                    Text(stringResource(R.string.first_name_label))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    showResult = false
                },
                label = {
                    Text(stringResource(R.string.last_name_label))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showResult = false
                },
                label = {
                    Text(stringResource(R.string.email_label))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                    showResult = false
                },
                label = {
                    Text(stringResource(R.string.birth_date_label))
                },
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

            Button(
                onClick = {
                    showResult = true
                },
                enabled = formComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_height)),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        colorResource(R.color.proceed_blue),
                    disabledContainerColor =
                        colorResource(R.color.proceed_gray),
                    contentColor =
                        colorResource(R.color.button_text),
                    disabledContentColor =
                        colorResource(R.color.button_text)
                )
            ) {
                Text(
                    text = stringResource(R.string.proceed_button)
                )
            }

            if (showResult) {
                Spacer(
                    modifier = Modifier.height(
                        dimensionResource(R.dimen.result_spacing)
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(R.dimen.result_padding)
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.result_spacing)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.result_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.form_text)
                    )

                    Text(
                        text = stringResource(
                            R.string.result_name,
                            firstName,
                            lastName
                        ),
                        color = colorResource(R.color.form_text)
                    )

                    Text(
                        text = stringResource(
                            R.string.result_email,
                            email
                        ),
                        color = colorResource(R.color.form_text)
                    )

                    Text(
                        text = stringResource(
                            R.string.result_birth_date,
                            birthDate
                        ),
                        color = colorResource(R.color.form_text)
                    )

                    Text(
                        text = stringResource(
                            R.string.result_experience,
                            experience
                        ),
                        color = colorResource(R.color.form_text)
                    )

                    Text(
                        text = stringResource(
                            R.string.result_skills,
                            selectedSkills.joinToString(
                                stringResource(R.string.list_separator)
                            )
                        ),
                        color = colorResource(R.color.form_text)
                    )
                }
            }
        }
    }
}