package com.explorer.transfer

import com.explorer.virtualFile.VirtualFile
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.lang.Exception

class MyTransferable(private val files: List<VirtualFile>) : Transferable {
    private val dataFlavor: DataFlavor = MyFileDataFlavour()

    override fun getTransferData(flavor: DataFlavor?): Any {
        return when (flavor) {
            is MyFileDataFlavour -> files
            else -> throw Exception("No data for the flavour: ${flavor?.humanPresentableName}")
        }
    }

    override fun isDataFlavorSupported(flavor: DataFlavor?): Boolean = flavor == dataFlavor

    override fun getTransferDataFlavors(): Array<DataFlavor> = arrayOf(dataFlavor)
}

class MyFileDataFlavour : DataFlavor(VirtualFile::class.java, "Virtual File Data flavour")