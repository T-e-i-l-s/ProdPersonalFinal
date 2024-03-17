package com.example.prodfinal.data.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import com.example.prodfinal.domain.model.LocationModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.delay

class Location(val context: Context) {
    // Функция получения локации пользователя
    fun getLocation(onFinish: (result: LocationModel) -> Unit) {
        if (!checkPermission()) {
            // Запрашиваем разрешения на голокацию
            requestPermissions(onFinish)
            onFinish(
                LocationModel(
                    false,
                    0.0,
                    0.0,
                )
            )
            return
        }

        locationRequest(onFinish)
    }

    fun locationRequest (onFinish: (result: LocationModel) -> Unit) {
        // Проверяем разрешения на использование геолокации
        if (checkPermission()) {
            // Проверяем голокацию
            if (isLocationEnabled()) {
                // Получаем FusedLocationProvider
                val location = LocationServices.getFusedLocationProviderClient(context)
                // Делаем запрос на геопозицию
                location.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token
                        override fun isCancellationRequested() = false
                    }
                ).addOnCompleteListener { task ->
                    // Возвращаем результат
                    onFinish(
                        LocationModel(
                            true,
                            task.result.latitude,
                            task.result.longitude
                        )
                    )
                }
            } else {
                // Возвращаем "неудачный" ответ
                onFinish(
                    LocationModel(
                        false,
                        0.0,
                        0.0,
                    )
                )
            }
        }
    }

    // Функция, которая проверяет разрешения
    fun checkPermission(): Boolean {
        val permission1 = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permission2 = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permission1 == PackageManager.PERMISSION_GRANTED &&
                permission2 == PackageManager.PERMISSION_GRANTED
    }

    // Функция, которая проверяем возможность использования геолокации
    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Функция, которая запрашивает разрешения на использование геолокации
    fun requestPermissions(onFinish: (result: LocationModel) -> Unit) {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(
            context as Activity,
            permissions,
            100
        )
    }
}