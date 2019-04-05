package com.manager.virtualFile

import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import java.io.File

fun getVirtualFile(path: String): VirtualFile? {
    if (path.isEmpty() || path.isBlank()) return null
    if (!path.startsWith('/') || !path.startsWith(File.separatorChar)) return null
    return LocalVirtualFile(path.toSystemDependentPath())
}