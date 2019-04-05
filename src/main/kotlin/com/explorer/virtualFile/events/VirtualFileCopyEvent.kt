package com.manager.virtualFile.events

import com.explorer.virtualFile.VirtualFile

class VirtualFileCopyEvent(
    val originalFile: VirtualFile,
    created: VirtualFile,
    requestor: Any
) : VirtualFileEvent(created, created.getParent()!!, requestor)