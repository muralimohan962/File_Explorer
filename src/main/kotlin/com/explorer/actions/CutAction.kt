package com.explorer.actions

import com.explorer.utils.systemClipboard
import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JList
import javax.swing.KeyStroke

class CutAction : AbstractJListAction("Cut", KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK)) {

    override fun handle(e: ActionEvent) {
        val list = e.source as JList<VirtualFile>
        val selectedFiles = list.selectedValuesList
        if (selectedFiles.isEmpty()) return

        val text = StringBuilder()
        text.append("Cut:")
        selectedFiles.forEach {
            text.append("${it.getPath().toSystemDependentPath()};")
        }

        systemClipboard.setContents(StringSelection(text.trim().toString())) { clipboard, contents ->  }
    }
}