package com.example.prodfinal.data.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.example.prodfinal.domain.model.LocationModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

private var onFinish: (result: LocationModel) -> Unit = {}

// Класс для работы с локацией

class Location(val context: Context) {
    // Функция для проверки разрешений и запроса локации
    fun getLocation(onFinishFun: (result: LocationModel) -> Unit) {
        // Передаем функцию onFinishFun в onFinish
        onFinish = onFinishFun
        if (!checkPermission()) { // Если нет разрешения на использование геолокации
            // Запрашиваем разрешения на геолокацию
            requestPermissions()
            return
        } else {
            // Делаем запрос на локацию
            locationRequest(onFinish)
        }
    }

    // Функция получения локации
    private fun locationRequest(onFinish: (result: LocationModel) -> Unit) {
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
    private fun checkPermission(): Boolean {
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
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Функция, которая запрашивает разрешения на использование геолокации
    private fun requestPermissions() {
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

    /*
    Функция, которая вызывается из MainActivity при
    получении результатов запроса разрешений
    для обработки результатов и повторного
    запроса локации(при необходимости)
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray
    ) {
        // Проверяем соответствие запроса
        if (requestCode == 100) {
            // Проверяем результаты запроса
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) { // Разрешение получено
                // Делаем повторный запрос на локацию
                locationRequest(onFinish)
            } else { // Нет разрешения
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
}