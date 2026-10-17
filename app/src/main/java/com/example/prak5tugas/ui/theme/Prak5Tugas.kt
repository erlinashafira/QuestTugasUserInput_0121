package com.example.prak5tugas.ui.theme

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.prak5tugas.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
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
    var showDatePicker by remember { mutableStateOf(false) }
    var experienceExpanded by remember { mutableStateOf(false) }

    val formComplete =
        firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() &&
                birthDate.isNotBlank() &&
                experience.isNotBlank() &&
                selectedSkills.isNotEmpty()

    // Hasil tampil 5 detik (nilai dari integers.xml) lalu hilang
    val resultDisplayDuration = integerResource(R.integer.result_display_duration)
    LaunchedEffect(showResult) {
        if (showResult) {
            delay(resultDisplayDuration.toLong())
            showResult = false
        }
    }

    // Tap di field tanggal juga membuka date picker
    val dateInteraction = remember { MutableInteractionSource() }
    LaunchedEffect(dateInteraction) {
        dateInteraction.interactions.collect {
            if (it is PressInteraction.Release) showDatePicker = true
        }
    }

    val dateFormatPattern = stringResource(R.string.date_format)
    val utcTimeZone = stringResource(R.string.timezone_utc)

    val textColor = colorResource(R.color.form_text)
    val titleColor = colorResource(R.color.form_title)
    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = titleColor,
        unfocusedBorderColor = colorResource(R.color.field_border),
        focusedPlaceholderColor = colorResource(R.color.field_label),
        unfocusedPlaceholderColor = colorResource(R.color.field_label),
        focusedTextColor = textColor,
        unfocusedTextColor = textColor,
        cursorColor = titleColor
    )

    val photoHeight = dimensionResource(R.dimen.photo_height)
    val cardOverlap = dimensionResource(R.dimen.card_overlap)
    val cardCorner = dimensionResource(R.dimen.card_corner)
    val dotCount = integerResource(R.integer.dot_count)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colorResource(R.color.form_background)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(R.drawable.foto),
                contentDescription = stringResource(R.string.photo_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(photoHeight)
            )

            // Indikator titik (titik pertama aktif)
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = dimensionResource(R.dimen.dot_margin),
                        top = dimensionResource(R.dimen.dot_margin)
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.dot_spacing)
                )
            ) {
                repeat(dotCount) { index ->
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.dot_size))
                            .clip(CircleShape)
                            .background(
                                if (index == 0) colorResource(R.color.dot_active)
                                else colorResource(R.color.dot_inactive)
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(photoHeight - cardOverlap))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.form_background),
                    shape = RoundedCornerShape(
                        topStart = cardCorner,
                        topEnd = cardCorner
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(
                            dimensionResource(R.dimen.form_padding)
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(R.dimen.form_spacing)
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.form_title),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = titleColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.form_subtitle),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(R.color.form_secondary_text),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(R.dimen.form_spacing))
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(R.dimen.form_spacing)
                            )
                        ) {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it; showResult = false },
                                placeholder = { Text(stringResource(R.string.first_name_label)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = fieldShape,
                                colors = fieldColors
                            )
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it; showResult = false },
                                placeholder = { Text(stringResource(R.string.last_name_label)) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = fieldShape,
                                colors = fieldColors
                            )
                        }

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it; showResult = false },
                            placeholder = { Text(stringResource(R.string.email_label)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(R.dimen.form_spacing)
                            )
                        ) {
                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(R.string.birth_date_label)) },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_calendar),
                                        contentDescription = stringResource(R.string.date_picker_description),
                                        tint = textColor
                                    )
                                },
                                interactionSource = dateInteraction,
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = fieldShape,
                                colors = fieldColors
                            )

                            ExposedDropdownMenuBox(
                                expanded = experienceExpanded,
                                onExpandedChange = { experienceExpanded = it },
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    value = experience,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text(stringResource(R.string.experience_label)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = experienceExpanded
                                        )
                                    },
                                    modifier = Modifier.menuAnchor(),
                                    singleLine = true,
                                    shape = fieldShape,
                                    colors = fieldColors
                                )
                                ExposedDropdownMenu(
                                    expanded = experienceExpanded,
                                    onDismissRequest = { experienceExpanded = false }
                                ) {
                                    experienceOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                experience = option
                                                experienceExpanded = false
                                                showResult = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = stringResource(R.string.skills_label),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            skillOptions.forEach { skill ->
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = skill in selectedSkills,
                                        onCheckedChange = { checked ->
                                            if (checked) {
                                                if (skill !in selectedSkills) selectedSkills.add(
                                                    skill
                                                )
                                            } else {
                                                selectedSkills.remove(skill)
                                            }
                                            showResult = false
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = titleColor,
                                            uncheckedColor = textColor
                                        )
                                    )
                                    Text(
                                        text = skill,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = textColor
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { showResult = true },
                            enabled = formComplete,
                            shape = fieldShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(R.dimen.button_height)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = titleColor,
                                disabledContainerColor = colorResource(R.color.proceed_gray),
                                contentColor = colorResource(R.color.button_text),
                                disabledContentColor = colorResource(R.color.button_text)
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.proceed_button),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (showResult) {
                            Dialog(onDismissRequest = { showResult = false }) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner)),
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorResource(R.color.result_background)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(
                                            dimensionResource(R.dimen.result_padding)
                                        ),
                                        verticalArrangement = Arrangement.spacedBy(
                                            dimensionResource(R.dimen.result_spacing)
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(R.string.result_title),
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = titleColor
                                        )
                                        Text(
                                            text = stringResource(
                                                R.string.result_name,
                                                firstName,
                                                lastName
                                            ),
                                            color = textColor
                                        )
                                        Text(
                                            text = stringResource(R.string.result_email, email),
                                            color = textColor
                                        )
                                        Text(
                                            text = stringResource(
                                                R.string.result_birth_date,
                                                birthDate
                                            ),
                                            color = textColor
                                        )
                                        Text(
                                            text = stringResource(
                                                R.string.result_experience,
                                                experience
                                            ),
                                            color = textColor
                                        )
                                        Text(
                                            text = stringResource(
                                                R.string.result_skills,
                                                selectedSkills.joinToString(
                                                    stringResource(R.string.list_separator)
                                                )
                                            ),
                                            color = textColor
                                        )
                                    }
                                }
                            }
                        }

                        if (showDatePicker) {
                            val datePickerState = rememberDatePickerState()
                            DatePickerDialog(
                                onDismissRequest = { showDatePicker = false },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            datePickerState.selectedDateMillis?.let { millis ->
                                                val formatter = SimpleDateFormat(
                                                    dateFormatPattern,
                                                    Locale.getDefault()
                                                )
                                                formatter.timeZone = TimeZone.getTimeZone(utcTimeZone)
                                                birthDate = formatter.format(Date(millis))
                                                showResult = false
                                            }
                                            showDatePicker = false
                                        }
                                    ) { Text(stringResource(R.string.dialog_ok)) }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDatePicker = false }) {
                                        Text(stringResource(R.string.dialog_cancel))
                                    }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }
                    }
                }
            }
        }
    }
}