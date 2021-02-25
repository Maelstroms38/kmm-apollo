package com.example.justdesserts.shared.logger

import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal

actual class MyLogger {
    actual fun logMessage(message: String) {
        _os_log_internal(__dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_DEFAULT,
            message)
    }
}