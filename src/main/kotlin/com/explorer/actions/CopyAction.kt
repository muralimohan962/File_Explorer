package com.explorer.actions

import com.explorer.utils.log
import com.explorer.utils.systemClipboard
import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JList
import javax.swing.KeyStroke

class CopyAction : AbstractJListAction("copy.action", KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val files = list.selectedValuesList
        if (files.isEmpty()) return

        val paths = files.map { it.getPath().toSystemDependentPath() }
        systemClipboard.setContents(StringSelection("Copy:${prepareForCopy(paths)}")) { clipboard, contents ->  }

        log(TAG, "Copied the files: ${prepareForCopy(paths)}")
    }

    private fun prepareForCopy(paths: List<String>): String {
        val builder = StringBuilder()

        paths.forEach { builder.append("$it;") }

        return builder.toString()
    }

    companion object {
        private const val TAG = "CopyAction"
    }
}