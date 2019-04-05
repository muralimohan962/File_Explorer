package com.explorer.actions

import com.explorer.ui.CreateFileOrDialogComponent
import com.explorer.ui.executeAction
import com.explorer.ui.getParentComponent
import com.explorer.ui.topList
import com.explorer.utils.hasContent
import com.explorer.utils.toSystemDependentPath
import com.explorer.virtualFile.VirtualFile
import com.manager.virtualFile.LocalVirtualFile
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*

class CreateNewDirectoryAction : AbstractJListAction(
    "Create.New.Directory",
    KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK or KeyEvent.SHIFT_DOWN_MASK)
) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val component = CreateFileOrDialogComponent()

        component.nameField.executeAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Create.Action") {
            component.createButton.actionListeners.forEach {
                it.actionPerformed(
                    ActionEvent(
                        component.createButton,
                        45,
                        "Command"
                    )
                )
            }
        }


        val parentComponent = getParentComponent(list, JFrame::class.java) ?: return
        val dialog = JDialog(parentComponent)

        component.createButton.addActionListener {
            if (component.nameField.text.hasContent()) createFile(e, component.nameField.text)
            else JOptionPane.showMessageDialog(
                getParentComponent(list, JFrame::class.java),
                "Please provide name!",
                "Provide name",
                JOptionPane.ERROR_MESSAGE
            )

            dialog.isVisible = false
            println("Creating...")
        }

        dialog.add(component.rootPanel)
        dialog.size = Dimension(300, 200)
        dialog.title = "Create File or Directory"
        component.createButton.requestFocusInWindow()
        dialog.isVisible = true
    }

    private fun createFile(e: ActionEvent, name: String) {
        val list = e.source as JList<VirtualFile>
        val file = list.selectedValue

        when {
            file != null -> {
                val parent = file.getParent()
                if (parent == null) (list.model as DefaultListModel<VirtualFile>).addElement(LocalVirtualFile("${File.separatorChar}$name").apply { createNativePeer() })
                else (list.model as DefaultListModel<VirtualFile>).addElement(LocalVirtualFile(parent.getPath() + "/" + name).apply { createNativePeer() })
            }
            list.model.size == 0 -> {
                val virtualFile = e.topList.selectedValue ?: return
                (list.model as DefaultListModel<VirtualFile>).addElement(LocalVirtualFile("${virtualFile.getPath()}/$name").apply { createNativePeer() })
            }
            else -> (list.model as DefaultListModel<VirtualFile>).addElement(
                LocalVirtualFile("${list.model.getElementAt(0).getParent()?.getPath()}/$name").apply { createNativePeer() })
        }
    }

    private fun LocalVirtualFile.createNativePeer() {
        val file = File(getPath().toSystemDependentPath())
        if (!file.exists())
            file.createNewFile()
    }
}