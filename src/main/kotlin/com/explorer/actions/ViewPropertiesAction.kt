package com.explorer.actions

import com.explorer.ui.IconsManager
import com.explorer.ui.ViewPropertiesComponent
import com.explorer.ui.getParentComponent
import com.explorer.utils.toIOFile
import com.explorer.virtualFile.VirtualFile
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JList
import javax.swing.KeyStroke

class ViewPropertiesAction : AbstractJListAction(
    "ViewPropertiesAction",
    KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK or KeyEvent.SHIFT_DOWN_MASK)
) {

    override fun handle(e: ActionEvent) {
        val list = (e.source as? JList<VirtualFile>) ?: return
        val selectedFiles = list.selectedValuesList
        if (selectedFiles.isEmpty() || selectedFiles.size > 1) return
        val file = selectedFiles.first()

        val component = ViewPropertiesComponent()
        component.nameField.text = file.getName()
        val ioFile = file.toIOFile()
        if (ioFile != null) {
            component.sizeField.text = IconsManager.getSize(file)
        } else
            component.sizeField.text = "Unknown"

        component.typeField.text =  IconsManager.getFileType(file)
        component.locationField.text = file.getPath().removeSuffix(file.getName()).removeSuffix("/")

        component.iconLabel.icon = IconsManager.getIcon(file)
        val parent = getParentComponent(list, JFrame::class.java)
        val dialog = JDialog(parent)
        dialog.add(component.rootPanel)
        dialog.size = component.rootPanel.preferredSize
        dialog.isVisible = true
    }
}