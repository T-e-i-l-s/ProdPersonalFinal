package com.example.prodfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.prodfinal.data.location.Location
import com.example.prodfinal.navigation.StackNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем резальтат запроса разрешений
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Отдаем его в класс Location для обработки и повторного запроса(при необходимости)
            Location(applicationContext).onRequestPermissionsResult(isGranted)
        }
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

        setContent {
            StackNavigator()
        }
    }
}