package com.explorer.utils

import java.io.File

fun File.rename(newName: String) {
    val pathElements = path.split(File.separatorChar).toMutableList()
    val lastIndex = pathElements.lastIndex

    pathElements.removeAt(lastIndex)
    pathElements[lastIndex] = newName

    val result = StringBuilder()

    result.append(File.separatorChar)

    pathElements.forEach {
        if (pathElements.indexOf(it) == pathElements.size - 1) result.append(it)
        else result.append(it + File.separatorChar)
    }

    renameTo(File(result.toString()))
}