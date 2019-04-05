package com.explorer.actions

import com.explorer.ui.FileManagerComponent
import com.explorer.utils.log
import com.explorer.utils.systemClipboard
import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import com.manager.virtualFile.LocalVirtualFile
import java.awt.datatransfer.DataFlavor
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.KeyStroke

class PasteAction : AbstractJListAction("Paste", KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK)) {

    override fun handle(e: ActionEvent) {
        var text = systemClipboard.getData(DataFlavor.stringFlavor).toString()
        var isMove: Boolean = false
        if (text.startsWith("Copy:")) {
            isMove = false
            text = text.removePrefix("Copy:")
        } else {
            if (text.startsWith("Cut:")) {
                isMove = true
                text = text.removePrefix("Cut:")
            }
        }
        val tokenizer = StringTokenizer(text, ";")
        val paths = mutableListOf<String>()

        while (tokenizer.hasMoreTokens())
            paths.add(tokenizer.nextToken())

        paths.forEach { println(it) }
        doPaste(paths.map { LocalVirtualFile(it) }, e.source as JList<VirtualFile>, isMove)
    }

    private fun doPaste(files: List<VirtualFile>, list: JList<VirtualFile>, isMove: Boolean) {
        if (files.isEmpty()) return
        try {
            files.forEach {
                val source = it.getPath().toSystemDependentPath()
                val target = getTargetPath(list).toSystemDependentPath() + File.separator + it.getName()
                println("Source: $source\tTarget: $target")

                if (isMove) {
                    Files.move(Paths.get(source), Paths.get(target))
                    log(TAG, "Moved the file $source to $target")
                } else {
                    Files.copy(Paths.get(source), Paths.get(target))
                    log(TAG, "Copied the file $source to $target")
                }
                (FileManagerComponent.getInstance().folderList.model as DefaultListModel<VirtualFile>).addElement(LocalVirtualFile(target))
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getTargetPath(list: JList<VirtualFile>): String {
        val target = list.selectedValue
        return if (target == null) {
            val file = FileManagerComponent.getInstance().topList.selectedValue
            if (file == null)
                println("The folder selected file is null!")
            file.getPath()
        } else
            target.getPath()
    }

    companion object {
        private const val TAG = "PasteAction"
    }
}