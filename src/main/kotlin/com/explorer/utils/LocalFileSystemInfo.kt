package com.explorer.utils

import com.explorer.ui.FileManagerComponent
import com.explorer.virtualFile.LocalFileSystem
import com.explorer.virtualFile.VirtualFile
import com.manager.virtualFile.LocalVirtualFile
import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

object LocalFileSystemInfo {

    fun getTopList(list: JList<VirtualFile>) {
        val rootFile =
            LocalFileSystem.getInstance().findFileByPath(LocalVirtualFile("/home/jetbrains").getPath()) ?: return
        val model = DefaultListModel<VirtualFile>()
        list.model = model
        list.cellRenderer = OurListCellRenderer()
        model.addElement(LocalVirtualFile("/home"))
        when {
            isWindows || isMac || isLinux -> {
                rootFile.getChildren()?.filter {
                    it.getName() == "Documents" ||
                            it.getName() == "Music" ||
                            it.getName() == "Pictures" ||
                            it.getName() == "Downloads" ||
                            it.getName() == "Videos" ||
                            it.getName() == "Home" ||
                            it.getName() == "Videos"
                }?.forEach {
                    model.addElement(it)
                }
            }
        }

        println("Root file:")
        if (rootFile.getChildren() == null)
            println("Null!")
        if (rootFile.getChildren().isNullOrEmpty())
            println("Empty!")
    }

     class OurListCellRenderer : ListCellRenderer<VirtualFile> {

        override fun getListCellRendererComponent(
            list: JList<out VirtualFile>?,
            value: VirtualFile?,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean
        ): Component =
            JLabel(
                value?.getName()!!.capitalize(),
                JLabel.HORIZONTAL
            ).apply {
                icon = getIcon(value)
                background = Color.DARK_GRAY
                isOpaque = true
                font = Font.getFont("Arial")
                border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
                foreground = Color.WHITE
                if (isSelected) {
                    foreground = Color.decode("#FF7D1D")
                    background = Color.WHITE
                }

                addMouseListener(object: MouseAdapter() {
                    override fun mouseClicked(e: MouseEvent?) {
                        if (e?.clickCount == 2) {
                            FileManagerComponent.setChildren(value, list as JList<VirtualFile>)
                        }
                    }
                })
            }
    }
}


 fun getIcon(file: VirtualFile): ImageIcon? {
     return when {
         file.getName().equals("Music", ignoreCase = true) -> ImageIcon("/home/jetbrains/IdeaProjects/File Manager/src/com.explorer.start.main/resources/music.png")
         file.getName().equals("Home", ignoreCase = true) -> ImageIcon("/home/jetbrains/IdeaProjects/File Manager/src/com.explorer.start.main/resources/home.png")
         else -> null
     }
}
