package com.explorer.utils

import com.explorer.virtualFile.VirtualFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun VirtualFile.toIOFile(): File? {
    return if (isValid()) File(getPath().toSystemDependentPath()) else null
}

fun delete(files: List<VirtualFile>, onSucess: () -> Unit = {}, onFailure: () -> Unit = {}) {
    try {
        files.forEach {
            delete(it)
        }
        onSucess()
    } catch (e: Exception) {
        onFailure()
    }
}

private fun delete(file: VirtualFile) {
    if (file.isDirectory()) {
        file.getChildren()?.forEach { delete(it) }
    } else Files.delete(Paths.get(file.getPath().toSystemDependentPath()))
}