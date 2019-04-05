package com.explorer.actions

import java.awt.Frame
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.JFileChooser
import javax.swing.KeyStroke

class MoveAction : AbstractJListAction("Move", KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK)) {

    override fun handle(e: ActionEvent) {
        val fileChooser = JFileChooser(File(File.separator))
        fileChooser.showDialog(Frame.getFrames().first(), "Choose")
    }
}