package com.explorer.transfer

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard

class MyClipboard(name: String) {


    companion object {

        fun getClipboard(): Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    }
}