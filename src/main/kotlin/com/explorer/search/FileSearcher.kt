package com.explorer.search

import java.io.File

class FileSearcher : QueryExecutor<File, FileSearcher.Parameters> {

    override fun execute(p: Parameters, consumer: (File) -> Boolean) {
        if (p.extension != null) doSearch(p.root, p.name, p.extension, consumer)
        else doSearch(p.root, p.name, consumer)
    }

    private fun doSearch(file: File, name: String, extension: String, consumer: (File) -> Boolean) {
        if (file.isDirectory)
            file.listFiles().forEach {
                doSearch(it, name, extension, consumer)
            }
        else if (file.name.contains(name, ignoreCase = true) && file.name.endsWith(extension, ignoreCase = false)) {
            if (!consumer(file))
                return
        }
    }

    private fun doSearch(file: File, name: String, consumer: (File) -> Boolean) {
        if (file.isDirectory)
            file.listFiles().forEach {
                doSearch(it, name, consumer)
            }
        else if (file.name.contains(name, ignoreCase = true)) {
            if (!consumer(file))
                return
        }
    }

    data class Parameters(val root: File, val name: String, val extension: String? = null)
}