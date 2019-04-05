package com.explorer.virtualFile

import com.explorer.utils.isWindows
import com.explorer.utils.toSystemDependentPath
import com.manager.virtualFile.LocalVirtualFile
import com.manager.virtualFile.VirtualFileListener
import com.manager.virtualFile.events.VirtualFileCopyEvent
import com.manager.virtualFile.events.VirtualFileEvent
import com.manager.virtualFile.events.VirtualFileMoveEvent
import com.manager.virtualFile.getVirtualFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

class LocalFileSystem private constructor() : VirtualFileSystem {
    private val virtualFileListeners = hashSetOf<VirtualFileListener>()
    private val rootPath = if (isWindows) "\\" else ""

    override fun deleteFile(requestor: Any, path: String) {
        val file = getVirtualFile(path)
        if (file != null)
            try {
                Files.delete(Paths.get(file.getPath().toSystemDependentPath()))
                val e = VirtualFileEvent(file, file.getParent()!!, requestor)
                virtualFileListeners.forEach { it.fileDeleted(e) }
            } catch (e: Exception) {
                println(e.message)
            }
    }

    override fun getRoot(): VirtualFile {
        return LocalVirtualFile("/")
    }

    override fun moveFile(requestor: Any, file: VirtualFile, to: VirtualFile) {
        try {
            val oldParent = file.getParent()!!
            Files.move(
                Paths.get(file.getPath().toSystemDependentPath()),
                Paths.get(to.getPath().toSystemDependentPath())
            )
            val e = VirtualFileMoveEvent(file, requestor, oldParent, to)
            virtualFileListeners.forEach { VirtualFileMoveEvent(file, requestor, oldParent, file.getParent()!!) }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun copyFile(requestor: Any, from: VirtualFile, to: VirtualFile) {
        try {
            Files.copy(
                Paths.get(from.getPath().toSystemDependentPath()),
                Paths.get(to.getPath().toSystemDependentPath())
            )
            val e = VirtualFileCopyEvent(from, to, requestor)
            virtualFileListeners.forEach { it.fileCopied(e) }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun findFileByPath(path: String): VirtualFile? =
        if (File(path.toSystemDependentPath()).exists()) LocalVirtualFile(path) else null

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return findFileByPath(path)
    }

    override fun addVirtualFileListener(listener: VirtualFileListener) {
        virtualFileListeners.add(listener)
    }

    override fun removeVirtualFileListener(listener: VirtualFileListener) {
        virtualFileListeners.remove(listener)
    }

    override fun createChildFile(requestor: Any, vDir: VirtualFile, name: String, isDir: Boolean): VirtualFile {
        val file = File(("${vDir.getPath()}/$name").toSystemDependentPath())
        if (!file.exists()) {
            if (isDir)
                file.mkdir()
            else
                file.createNewFile()
        }
        return LocalVirtualFile(file.absolutePath)
    }

    override fun getOutputStream(file: VirtualFile): OutputStream? {
        val f = findFileByPath(file.getPath())
        return if (f != null) FileOutputStream(f.getPath().toSystemDependentPath()) else null
    }

    override fun getInputStream(file: VirtualFile): InputStream? {
        val f = findFileByPath(file.getPath())
        return if (f != null) FileInputStream(f.getPath().toSystemDependentPath()) else null
    }

    companion object {
        private val INSTANCE = LocalFileSystem()

        fun getInstance(): LocalFileSystem = INSTANCE
    }
}
