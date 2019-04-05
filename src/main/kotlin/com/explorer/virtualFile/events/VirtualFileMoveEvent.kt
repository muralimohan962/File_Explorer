package com.manager.virtualFile.events

import com.explorer.virtualFile.VirtualFile

class VirtualFileMoveEvent(
    file: VirtualFile,
    requestor: Any,
    val oldParent: VirtualFile,
    val newParent: VirtualFile
) : VirtualFileEvent(file, file.getParent()!!, requestor)