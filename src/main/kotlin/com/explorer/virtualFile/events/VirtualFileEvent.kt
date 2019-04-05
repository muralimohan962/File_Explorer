package com.manager.virtualFile.events

import com.explorer.virtualFile.VirtualFile

open class VirtualFileEvent(val file: VirtualFile, val parent: VirtualFile, val requestor: Any)