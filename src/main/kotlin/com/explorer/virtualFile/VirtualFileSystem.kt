package com.explorer.virtualFile

import com.manager.virtualFile.VirtualFileListener
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Represents a virtual file system used by the file manager.
 */
interface VirtualFileSystem {

    fun deleteFile(requestor: Any, path: String)

    fun moveFile(requestor: Any, file: VirtualFile, to: VirtualFile)

    fun copyFile(requestor: Any, from: VirtualFile, to: VirtualFile)

    fun findFileByPath(path: String): VirtualFile?

    fun refreshAndFindFileByPath(path: String): VirtualFile?

    fun extractPresentableUrl(path: String) = path.replace('/', File.separatorChar)

    fun addVirtualFileListener(listener: VirtualFileListener)

    fun removeVirtualFileListener(listener: VirtualFileListener)

    fun createChildFile(requestor: Any, vDir: VirtualFile, name: String, isDir: Boolean = false): VirtualFile

    fun getOutputStream(file: VirtualFile): OutputStream?

    fun getInputStream(file: VirtualFile): InputStream?

    fun getRoot(): VirtualFile
}