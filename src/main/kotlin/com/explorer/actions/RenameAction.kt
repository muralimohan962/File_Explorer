package com.explorer.actions

import com.explorer.ui.getParentComponent
import com.explorer.utils.hasContent
import com.explorer.utils.offsetOf
import com.explorer.virtualFile.VirtualFile
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

class RenameAction : AbstractJListAction(
    "Rename.Action",
    KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK)
) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val files = list.selectedValuesList

        if (files.isEmpty() || files.size > 1) return

        val nameField = JTextField()
        val renameBtn= JButton("Rename")
        val panel = JPanel(GridLayout(2, 1, 10, 10))
        panel.add(nameField)
        panel.add(renameBtn)

        val dialog = JDialog(getParentComponent(list, JFrame::class.java))
        dialog.add(panel)

        renameBtn.addActionListener {
            if (!nameField.text.hasContent())
                println("Could not rename!\nPlease enter a name.")
            else
                println("Renaming too ${nameField.text}.")

        }

        nameField.text = files.first().getName()
        nameField.select(0, nameField.text.offsetOf('.') - 1)

        dialog.size = Dimension(300, 300)
        dialog.isVisible = true
    }
}