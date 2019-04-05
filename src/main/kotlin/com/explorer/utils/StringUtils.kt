package com.explorer.utils

import java.io.File

enum class System(val pathSeparator: Char) {
    WINDOWS('/'), MAC('/'), LINUX('/')
}

fun toSystemIndependentPath(path: String, system: System): String {
    return when(system) {
        System.WINDOWS -> path.replace(System.WINDOWS.pathSeparator, '/')
        else -> path
    }
}

fun getNameFromPath(path: String, system: System): String {
    val systemIndependentPath = toSystemIndependentPath(path, system)
    return systemIndependentPath.split("/").last()
}

fun toSystemDependentPath(path: String, system: System): String {
    return when(system) {
        System.WINDOWS -> path.replace('/', System.WINDOWS.pathSeparator)
        else -> path
    }
}

fun String.toSystemIndependentPath(): String = replace(File.separatorChar, '/')

fun String.toSystemDependentPath(): String = replace('/', File.separatorChar)

private fun String.isSystemDependent(): Boolean = contains(File.separatorChar)

fun String.getNameFromPath(): String = if (isEmpty() || isBlank()) "" else if (isSystemDependent()) split(File.separatorChar).last() else split('/').last()


fun String.getPresentableName(): String {
    return when {
        isEmpty() || isBlank() -> ""
        length <= 10 -> substring(0 until length)
        length in 11..20 -> "<html>" + substring(0..10) + "<br>" + substring(11 until length) + "</html>"
        else -> "<html>" + substring(0..10) + "<br>" + substring(11 until length) + "</html>"
    }
}

fun String.hasContent() = isNotBlank() && isNotBlank()

fun String.offsetOf(char: Char): Int {
    var offset = -1
    forEachIndexed {  index, c ->
        if (c == char) {
            offset = index
            return@forEachIndexed
        }
    }

    if (offset != -1) return offset

    throw Exception("No such character found!")
}