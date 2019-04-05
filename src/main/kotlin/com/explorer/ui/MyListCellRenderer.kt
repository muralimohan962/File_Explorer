package com.explorer.ui

import com.explorer.utils.getPresentableName
import com.explorer.virtualFile.VirtualFile
import java.awt.Color
import java.awt.Component
import javax.swing.BorderFactory
import javax.swing.JList
import javax.swing.ListCellRenderer

class MyListCellRenderer : ListCellRenderer<VirtualFile> {

    override fun getListCellRendererComponent(
        list: JList<out VirtualFile>?,
        value: VirtualFile?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val ui = ListUIElement()
        ui.iconLabel.icon = IconsManager.getIcon(value!!)
        ui.textLabel.text = value.getName().getPresentableName()

        if (isSelected) {
            ui.rootPanel.background = Color.decode("#01C4FF")
            ui.textLabel.foreground = Color.decode("#575856")
        } else
            ui.rootPanel.background = Color.WHITE

        return ui.rootPanel.apply {
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        }
    }

}