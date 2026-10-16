package com.example.prak5tugas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.prak5tugas.ui.theme.Biodata
import com.example.prak5tugas.ui.theme.Prak5TugasTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prak5TugasTheme {
                Biodata()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BiodataPreview() {
    Prak5TugasTheme {
        Biodata()
    }
}