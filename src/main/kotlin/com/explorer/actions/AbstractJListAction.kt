package com.explorer.actions

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JList
import javax.swing.KeyStroke

abstract class AbstractJListAction(val actionName: String, val keyStroke: KeyStroke) : AbstractAction() {

    final override fun actionPerformed(e: ActionEvent?) {
        if (e == null || e.source !is JList<*>) return
        handle(e)
    }

    abstract fun handle(e: ActionEvent)
}