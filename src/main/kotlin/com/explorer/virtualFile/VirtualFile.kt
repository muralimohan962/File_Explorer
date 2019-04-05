package com.explorer.virtualFile

import com.explorer.utils.FileIconProvider
import com.explorer.utils.toSystemDependentPath
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import javax.swing.Icon

/**
 *  Represents the system-independent file.
 *  @author Murali
 *  @author Sree hari
 *  @author Sony
 *  @author Sushma
 */
interface VirtualFile {
    /**
     * Returns the name of this file.
     * @return the name of this file.
     */
    fun getName(): String

    /**
     * Returns the system-independent path of this file.
     * @return the system-independent path of this file.
     */
    fun getPath(): String

    /**
     * Returns the children of this file.
     * @return the children of this file or null if this is the leaf.
     */
    fun getChildren(): List<VirtualFile>?

    /**
     * Returns the parent of this file.
     * @return the parent of this file or null if this is the root.
     */
    fun getParent(): VirtualFile?

    fun isHidden(): Boolean = try {
        Files.isHidden(Paths.get(getPath().toSystemDependentPath()))
    } catch (e: Exception) {
        false
    }

    /**
     * Returns the extension of this file without preceding '.' .
     */
    fun getExtension(): String?

    fun getContent(): String?

    fun isValid(): Boolean

    fun getIcon(): Icon = FileIconProvider.getIcon(this)

    /**
     * Returns whether this file is a directory or not
     * @return true if this file is directory or false otherwise.
     */
    fun isDirectory(): Boolean

    fun rename(requestor: Any, newName: String)

    fun move(requestor: Any, from: VirtualFile, to: VirtualFile)

    fun copy(requestor: Any, from: VirtualFile, to: VirtualFile)

    fun delete(requestor: Any, file: VirtualFile)

    fun getProperties(): BasicFileAttributes?

    fun getOutputStream(): OutputStream?

    fun getInputStream(): InputStream?
}