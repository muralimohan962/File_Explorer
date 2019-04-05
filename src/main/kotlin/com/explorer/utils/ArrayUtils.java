package com.explorer.utils;

import com.explorer.virtualFile.VirtualFile;

import java.util.List;

public class ArrayUtils {

    public static VirtualFile[][] getElement(List<VirtualFile> files) {
        VirtualFile[][] elements = new VirtualFile[getRowCount(files)][files.size()];


        for (int i = 0; i < files.size(); i++) {

        }

        return elements;
    }

    private static int getRowCount(List<VirtualFile> files) {
        int rowCount = 0;
        rowCount += files.size() / 18;
        rowCount += files.size() % 18;
        return rowCount;
    }
}
