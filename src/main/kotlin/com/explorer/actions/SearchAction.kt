package com.explorer.actions

import com.explorer.concurrency.AppExecUtil
import com.explorer.ui.FileManagerComponent
import com.explorer.ui.SearchComponent
import com.explorer.ui.getParentComponent
import com.explorer.utils.hasContent
import com.explorer.utils.toIOFile
import com.explorer.virtualFile.VirtualFile
import com.manager.virtualFile.LocalVirtualFile
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*

class SearchAction : AbstractJListAction(
    "Search Action",
    KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)
) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val searchComponent = SearchComponent()
        val searchRoot = findRoot(list)
        if (searchRoot == null) {
            println("Could not find the search root!")
            return
        }
        val frame = getParentComponent(list, JFrame::class.java) ?: return

        val dialog = JDialog(frame)
        dialog.add(searchComponent.rootPanel, BorderLayout.CENTER)
        dialog.size = Dimension(300, 200)

        searchComponent.searchButton.addActionListener {
            dialog.isVisible = false
            val fileName = searchComponent.fileName
            val fileExtension = searchComponent.fileExtension

            AppExecUtil.getExecutorService().submit {
                val result = arrayListOf<File>()
                if (!fileExtension.hasContent()) {
                    findFiles(searchRoot) {
                        if (it.name.contains(fileName, ignoreCase = true) && it.name.endsWith(
                                fileExtension,
                                ignoreCase = false
                            )
                        )
                            result.add(it)

                        result.size != 30
                    }
                } else {
                    findFiles(searchRoot) {
                        if (it.name.contains(fileName, ignoreCase = true))
                            result.add(it)

                        result.size != 30
                    }
                }

                if (result.isEmpty())
                    JOptionPane.showMessageDialog(
                        frame,
                        "Could not find the file!",
                        "Sorry",
                        JOptionPane.INFORMATION_MESSAGE
                    )
                else
                    EventQueue.invokeLater {
                        FileManagerComponent.setChildren(
                            LocalVirtualFile(result.first().absolutePath),
                            list
                        )
                    }
            }
        }


        dialog.setLocation(frame.width / 2, frame.height / 2)
        dialog.isVisible = true
    }


    companion object {
        fun findFiles(rootFile: File, consumer: (File) -> Boolean) {
            if (!consumer(rootFile))
                return
            if (rootFile.isDirectory)
                rootFile.listFiles()?.forEach { findFiles(it, consumer) }
        }

        private fun findRoot(list: JList<VirtualFile>): File? {
            val selectedFiles = list.selectedValuesList

            return when {
                selectedFiles.isEmpty() -> if (list.model.size == 0) return null else list.model.getElementAt(0).getParent()?.toIOFile()
                selectedFiles.size > 1 -> selectedFiles.first().getParent()?.toIOFile()
                else -> selectedFiles.first()?.toIOFile()
            }
        }
    }
}