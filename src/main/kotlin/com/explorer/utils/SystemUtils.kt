package com.explorer.utils

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard

val systemClipboard: Clipboard
    get() = Toolkit.getDefaultToolkit().systemClipboard