package com.manager.virtualFile

import com.explorer.utils.getNameFromPath
import com.explorer.utils.toSystemDependentPath
import com.explorer.utils.toSystemIndependentPath
import com.explorer.virtualFile.VirtualFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes

/**
 * Represents the windows virtual file.
 * @author Murali
 */

class LocalVirtualFile(path: String) : VirtualFile {
    private var path = path

    override fun getName(): String = getPath().getNameFromPath()

    override fun getPath(): String = path.toSystemIndependentPath()

    override fun getChildren(): List<VirtualFile>? {
        val file = File(path)
        val result = mutableListOf<VirtualFile>()

        val children = file.listFiles() ?: return null
        if (children.isEmpty()) return emptyList()

        for (f in children)
            result.add(LocalVirtualFile(f.absolutePath))

        return result
    }

    override fun getParent(): VirtualFile? =
        if (File(path).parentFile == null) null else LocalVirtualFile(File(path).parentFile.absolutePath.toSystemIndependentPath())

    override fun getContent(): String? {
        return if (isDirectory()) null
        else File(path.toSystemDependentPath()).readText()
    }

    override fun getExtension(): String? = if (isDirectory()) null else getPath().getNameFromPath().split(".").last()

    override fun isDirectory(): Boolean = File(getPath().toSystemDependentPath()).isDirectory

    override fun move(requestor: Any, from: VirtualFile, to: VirtualFile) {
        if (getChildren() == null || !isDirectory()) return
        if (!getChildren()!!.contains(from) || !getChildren()!!.contains(to)) return

        try {
            Files.move(
                Paths.get(from.getPath().toSystemDependentPath()),
                Paths.get(to.getPath().toSystemDependentPath())
            )
        } catch (e: Exception) {
            println("Error moving file from ${from.getPath()} to ${to.getPath()}: ${e.message}")
        }
    }

    override fun copy(requestor: Any, from: VirtualFile, to: VirtualFile) {
        if (getChildren() == null || !isDirectory()) return
        if (!getChildren()!!.contains(from) || !getChildren()!!.contains(to)) return

        try {
            Files.copy(
                Paths.get(from.getPath().toSystemDependentPath()),
                Paths.get(to.getPath().toSystemDependentPath())
            )
        } catch (e: Exception) {
            println("Error copying file from ${from.getPath()} to ${to.getPath()}: ${e.message}")
        }
    }

    override fun rename(requestor: Any, newName: String) {
        try {
            val resultantPath = removeLastElementAndGetPath() + File.separatorChar + newName
            Files.move(
                Paths.get(getPath().toSystemDependentPath()),
                Paths.get(resultantPath)
            )
            path = resultantPath
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override fun isValid() = try {
        File(getPath().toSystemDependentPath()).exists()
    } catch (e: Exception) {
        false
    }

    override fun delete(requestor: Any, file: VirtualFile) {
        if (!isDirectory() || getChildren() == null) return

        try {
            Files.delete(Paths.get(file.getPath().toSystemDependentPath()))
        } catch (e: Exception) {
            println("Error deleting the file ${file.getPath()}: ${e.message}")
        }
    }

    override fun getProperties(): BasicFileAttributes? {
        return try {
            Files.readAttributes(Paths.get(getPath().toSystemDependentPath()), BasicFileAttributes::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun getOutputStream(): OutputStream? =
        if (isDirectory()) null else FileOutputStream(getPath().toSystemDependentPath())

    override fun getInputStream(): InputStream? =
        if (isDirectory()) null else FileInputStream(getPath().toSystemDependentPath())


    private fun removeLastElementAndGetPath(): String {
        val path = getPath().toSystemDependentPath()
        val elements = path.split(File.separatorChar).toMutableList()
        elements.removeAt(elements.lastIndex)
        val result = StringBuilder()

        result.append(File.separatorChar)
        elements.forEach {
            if (elements.indexOf(it) == elements.size - 1)
                result.append(it)
            else
                result.append(it + File.separatorChar)
        }

        return result.toString()
    }

    override fun toString(): String {

        return getName().capitalize()
    }
}
