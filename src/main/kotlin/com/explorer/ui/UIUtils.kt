package com.explorer.ui

import com.explorer.virtualFile.VirtualFile
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*

fun createPopupMenu(): JPopupMenu = JPopupMenu().apply {
    background = Color.decode("#F8FFB0")
    foreground = Color.BLACK
}

val font = Font.createFont(
    Font.TRUETYPE_FONT,
    File("/home/jetbrains/IdeaProjects/File Explorer/src/main/resources/fonts/HelveticaNeue Thin.ttf")
).deriveFont(Font.PLAIN, 18f)

fun initUI() {
    UIManager.put("Label.font", font)
}

fun JList<VirtualFile>.setModel(file: VirtualFile) {
    model = DefaultListModel<VirtualFile>().apply {
        file.getParent()?.getChildren()?.forEach { addElement(it) }
    }

    setSelectedValue(file, true)
}

fun <T> getParentComponent(component: JComponent, clazz: Class<T>): T? {
    return try {
        var parent = component.parent

        while (parent::class.java !== clazz) {
            parent = parent.parent
        }

        parent as? T
    } catch (e: Exception) {
        null
    }
}

fun JTextField.executeAction(ks: KeyStroke, actionName: String, action: (JTextField) -> Unit) {
    inputMap.put(ks, actionName)
    actionMap.put(action, object : AbstractAction() {
        override fun actionPerformed(e: ActionEvent?) {
            println("Executing action")
            action(e?.source as JTextField)
        }
    })
}

val ActionEvent.topList: JList<VirtualFile>
    get() = FileManagerComponent.getInstance().topList