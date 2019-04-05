package com.explorer.utils

import java.lang.System

val isWindows: Boolean
    get() {
        return System.getProperty("os.name").contains("Windows", ignoreCase = true)
    }

val isMac: Boolean
    get() {
        return System.getProperty("os.name").contains("Mac", ignoreCase = true)
    }

val isLinux: Boolean
    get() {
        return System.getProperty("os.name").contains("Linux", ignoreCase = true)
    }

