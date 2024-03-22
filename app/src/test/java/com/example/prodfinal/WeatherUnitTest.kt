package com.example.prodfinal

import com.example.prodfinal.data.mapper.WeatherMapper
import com.example.prodfinal.domain.model.WeatherModel
import org.junit.Test

import org.junit.Assert.*

class WeatherUnitTest {
    @Test
    fun defaultWeatherMapperTest() {
        val source = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "10",
            "8",
            "11",
            "7",
        )
        val expected = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "10°C",
            "8°C",
            "11°C",
            "Ощущается как 7°C",
        )
        val result = WeatherMapper().invoke(source)
        assertEquals(expected, result)
    }

    @Test
    fun notRoundTempWeatherMapperTest() {
        val source = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "10.41",
            "8.83",
            "11.72",
            "7.02",
        )
        val expected = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "10°C",
            "9°C",
            "12°C",
            "Ощущается как 7°C",
        )
        val result = WeatherMapper().invoke(source)
        assertEquals(expected, result)
    }

    @Test
    fun minusTempWeatherMapperTest() {
        val source = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "-10.41",
            "-8.83",
            "-11.72",
            "-7.00",
        )
        val expected = WeatherModel(
            "Москва",
            "Ясно",
            "",
            "-10°C",
            "-9°C",
            "-12°C",
            "Ощущается как -7°C",
        )
        val result = WeatherMapper().invoke(source)
        assertEquals(expected, result)
    }
}