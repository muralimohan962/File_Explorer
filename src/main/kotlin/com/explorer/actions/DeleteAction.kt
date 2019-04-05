package com.explorer.actions

import com.explorer.utils.log
import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.KeyStroke

class DeleteAction : AbstractJListAction("Delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val files = list.selectedValuesList
        if (files.isEmpty()) return

        files.forEach {
            try {
                deleteFile(it)
            } catch (e: Exception) {
                log(TAG, e)
                println("Could not delete the file ${it.getName()}\nReason: ${e.message}")
                throw e
            }
        }

        val model = list.model as DefaultListModel<VirtualFile>
        files.forEach { model.removeElement(it) }
        files.filter { it.isValid() }.forEach { model.addElement(it) }
    }

    private fun deleteFile(file: VirtualFile) {
        try {
            if (!file.isDirectory() || file.getChildren() == null || file.getChildren()?.isEmpty()!!)
                Files.delete(Paths.get(file.getPath().toSystemDependentPath()))
            else file.getChildren()?.forEach { deleteFile(it) }
        } catch (e: Exception) {
            log(TAG, e)
        }
    }


    companion object {
        private const val TAG = "DeleteAction"
    }
}