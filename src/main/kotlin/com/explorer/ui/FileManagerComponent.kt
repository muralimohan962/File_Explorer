package com.explorer.ui

import com.explorer.actions.*
import com.explorer.utils.LocalFileSystemInfo
import com.explorer.virtualFile.LocalFileSystem
import com.explorer.virtualFile.VirtualFile
import com.explorer.virtualFile.VirtualFileSystem
import com.manager.virtualFile.LocalVirtualFile
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class FileManagerComponent private constructor() : MouseAdapter(), ListSelectionListener {
    private val fileManagerComponent = FileManagerMainComponent()
    val folderList = fileManagerComponent.folderList
    val topList = fileManagerComponent.topList
    val folderModel = folderList.model
    private val topModel = folderList.model
    private val fileSystem = LocalFileSystem.getInstance() as VirtualFileSystem

    init {
        folderList.cellRenderer = MyListCellRenderer()
        folderList.model = DefaultListModel<VirtualFile>().apply {
            fileSystem.getRoot().getChildren()?.forEach {
                addElement(it)
            }
        }

        folderList.fixedCellWidth = 160
        initTopList()
        folderList.addListSelectionListener(this)
        folderList.visibleRowCount = -1
        setAction(folderList)
        topList.addListSelectionListener(this)

        folderList.addMouseListener(this)
        topList.addMouseListener(this)
        folderList.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        topList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        folderList.layoutOrientation = JList.HORIZONTAL_WRAP
        topList.layoutOrientation = JList.VERTICAL
    }

    fun getComponent(): JComponent = fileManagerComponent.rootPanel

    private fun initTopList() {
        val model = DefaultListModel<VirtualFile>()
        val home = LocalVirtualFile("/home")
        model.addElement(home)
        LocalVirtualFile("/home/jetbrains").getChildren()?.filter {
            it.getName() == "Documents"
                    || it.getName() == "Music"
                    || it.getName() == "Pictures"
                    || it.getName() == "Videos"
                    || it.getName() == "Downloads"
        }?.forEach { model.addElement(it) }
        topList.cellRenderer = LocalFileSystemInfo.OurListCellRenderer()
        topList.setSelectedValue(home, true)
        SwingUtilities.invokeLater {
            topList.model = model
        }
    }

    override fun valueChanged(e: ListSelectionEvent) {
        when (e.source) {
            topList -> {
                val file = topList.selectedValue ?: return
                setChildren(file, folderList)
            }
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        when (e.source) {
            folderList -> {
                testSelection(e)
                val detector = GestureDetector(e)
                if (detector.isDoubleClick()) {
                    val file = folderList.selectedValue ?: return
                    setChildren(file, folderList)
                }
            }
        }
    }

    private fun testSelection(e: MouseEvent) {
        val list = e.source as JList<*>
        val rectangle = list.getCellBounds(0, list.model.size - 1)
        if (!rectangle.contains(e.point)) {
            list.clearSelection()
            list.selectionModel.anchorSelectionIndex = -1
            list.selectionModel.leadSelectionIndex = -1
        }
    }

    companion object {
        private val fileManagerComponent = FileManagerComponent()

        fun getInstance(): FileManagerComponent = fileManagerComponent

        fun setChildren(file: VirtualFile, list: JList<VirtualFile>) {
            if (!file.isDirectory()) return
            SwingUtilities.invokeLater { list.model = createModel(file) }
        }

        fun createModel(file: VirtualFile): DefaultListModel<VirtualFile> {
            if (!file.isDirectory()) return DefaultListModel()
            val model = DefaultListModel<VirtualFile>()
            file.getChildren()?.forEach {
                model.addElement(it)
            }
            return model
        }

        private fun setAction(list: JList<VirtualFile>) {
            val actions = listOf(CutAction(), PasteAction(), CopyAction(), SearchAction(), ViewPropertiesAction(), RenameAction(),  DeleteAction(), CreateNewDirectoryAction())

            actions.forEach {
                list.inputMap.put(it.keyStroke, it.actionName)
                list.actionMap.put(it.actionName, it)
            }
        }
    }
}