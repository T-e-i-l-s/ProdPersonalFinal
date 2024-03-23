package com.example.prodfinal.domain.authorization

import android.util.Base64
import java.nio.charset.StandardCharsets

// Класс для хеширования паролей

class Hash {
    // Хешируем строку
    fun encode (line: String): String {
        return Base64.encodeToString(
            line.toByteArray(StandardCharsets.UTF_8),
            Base64.DEFAULT
        )
    }

    // Дехешируем строку
    fun decode (line: String): String {
        val decoded = Base64.decode(line, Base64.DEFAULT)
        return String(decoded, StandardCharsets.UTF_8)
    }
}