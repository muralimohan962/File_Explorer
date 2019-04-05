package com.explorer.ui

import java.awt.event.MouseEvent

class GestureDetector(val e: MouseEvent) {

    fun isDoubleClick(): Boolean = e.clickCount == 2

    fun isRightClick(): Boolean = e.button == MouseEvent.BUTTON3

    fun isLeftClick(): Boolean = e.button == MouseEvent.BUTTON1
}