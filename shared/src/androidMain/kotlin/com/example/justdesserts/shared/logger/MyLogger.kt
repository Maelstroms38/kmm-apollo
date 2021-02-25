package com.example.justdesserts.shared.logger

actual class MyLogger {
    actual fun logMessage(message: String) {
        println(message)
    }
}