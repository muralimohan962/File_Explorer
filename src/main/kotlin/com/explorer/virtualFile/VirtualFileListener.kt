package com.manager.virtualFile

import com.manager.virtualFile.events.VirtualFileCopyEvent
import com.manager.virtualFile.events.VirtualFileEvent
import com.manager.virtualFile.events.VirtualFileMoveEvent

interface VirtualFileListener {

    fun fileDeleted(e: VirtualFileEvent)

    fun fileMoved(e: VirtualFileMoveEvent)

    fun fileCopied(e: VirtualFileCopyEvent)

    fun fileCreated(e: VirtualFileEvent)

    fun contentsChanged(e: VirtualFileEvent)
}