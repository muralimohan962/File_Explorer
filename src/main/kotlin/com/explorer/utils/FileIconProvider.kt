package com.explorer.utils

import com.explorer.virtualFile.VirtualFile
import java.io.File
import javax.swing.ImageIcon

object FileIconProvider {

    fun getIcon(file: VirtualFile): ImageIcon {
        val ioFile = file.toIOFile() ?: return ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder.png")
        return FileIconProvider.getIcon(ioFile)
    }

    fun getIcon(file: File): ImageIcon {
        return when {
            file.isDirectory -> ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder.png")
            !file.isDirectory -> ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder.png")
            else -> ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder.png")
        }
    }
}