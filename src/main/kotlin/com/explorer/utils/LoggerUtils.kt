package com.explorer.utils

import java.io.File
import java.io.PrintStream

private val logger = PrintStream(File("/home/jetbrains/explorer.log").run {
    if (!exists())
        createNewFile()
    outputStream()
})

fun log(TAG: String, message: String) {
    logger.println("$TAG: $message")
}

fun log(TAG: String, e: Throwable) {
    log(TAG, e.message ?: "")
}