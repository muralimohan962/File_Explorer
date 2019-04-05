package com.explorer.start

import com.explorer.search.FileSearcher
import com.explorer.search.QueryExecutor
import com.explorer.ui.FileManagerComponent
import com.explorer.ui.initUI
import java.io.File
import javax.swing.JFrame

fun foo() {
    initUI()
    val frame = JFrame()
    frame.run {
        add(FileManagerComponent.getInstance().getComponent())
        setSize(1000, 500)
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        isVisible = true
    }
}


fun search(file: File, name: String, extension: String? = null) {
   val queryExecutor: QueryExecutor<File, FileSearcher.Parameters> =
       FileSearcher()
   val result = arrayListOf<String>()

   queryExecutor.execute(FileSearcher.Parameters(file, name, extension)) {
       result.add(it.absolutePath)
       result.size != 10000
   }


   result.forEach { println(File(it).name) }
}