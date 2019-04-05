package com.explorer.ui

import com.explorer.utils.toIOFile
import com.explorer.virtualFile.VirtualFile
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import javax.swing.Icon
import javax.swing.ImageIcon

object IconsManager {
    private val FILLED_FOLDER_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder/folder-fix2.png")
    private val EMPTY_FOLDER_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/folder/folder-fix.png")
    private val PDF_DOC_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/pdf_ic.png")
    private val IMAGE_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/image_ic.png")
    private val UNKNOW_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/unknown_ic.png")
    private val MUSIC_FILE_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/music_ic.png")
    private val VIDEO_FILE_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/video_ic.png")
    private val DOCUMENT_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/document_ic.png")
    private val JAVA_FILE_ICON =
        ImageIcon("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/icons/java_ic.png")

    fun getIcon(file: VirtualFile): Icon {
        return when {
            file.getName().endsWith(".pdf") -> PDF_DOC_ICON
            file.getName().endsWith(".img") || file.getName().endsWith(".png") || file.getName().endsWith(".svg") -> IMAGE_ICON
            file.isDirectory() && file.getChildren() == null -> EMPTY_FOLDER_ICON
            file.isDirectory() && file.getChildren() != null -> FILLED_FOLDER_ICON
            file.getName().endsWith(".mp3") -> MUSIC_FILE_ICON
            file.getName().endsWith(".mp4") -> VIDEO_FILE_ICON
            file.getName().endsWith(".java") -> JAVA_FILE_ICON
            else -> UNKNOW_ICON
        }
    }

    fun getFileType(file: VirtualFile): String = when {
        file.getName().endsWith(".pdf") -> "Pdf"
        !file.getName().contains(".") -> "Directory"
        file.getName().endsWith(".mp3") -> "Audio"
        file.getName().endsWith(".mp4") -> "Video"
        file.getName().endsWith(".java") -> "Java Source"
        file.getName().endsWith(".img") || file.getName().endsWith(".png") || file.getName().endsWith(".svg") -> "Image"
        else -> "Unknown"
    }

    fun getSize(file: VirtualFile): String {
        val ioFile = file.toIOFile() ?: return "0 bytes"
        val attributes = Files.readAttributes(ioFile.toPath(), BasicFileAttributes::class.java)
        println("Size: ${attributes.size()}")
        return IconsManager.getSize(attributes.size())
    }

    private fun getSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes bytes"
            (bytes / 1024) < 1024 -> "${bytes / 1024} kilobytes"
            ((bytes / 1024) / 1024) < 1024 -> "${((bytes / 1024) / 1024)} megabytes"
            else -> "${((bytes / 1024) / 1024 / 1024)} gigabytes"
        }
    }
}