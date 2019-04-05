package com.explorer.ui;

import com.explorer.virtualFile.VirtualFile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class FileManagerMainComponent {
    private JList<VirtualFile> topList;
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JList<VirtualFile> folderList;

    public FileManagerMainComponent() {
        topList.setFixedCellWidth(200);
        scrollPane.setBackground(Color.gray);
        topList.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
        folderList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    }

    public JList<VirtualFile> getTopList() {
        return topList;
    }

    public JList<VirtualFile> getFolderList() {
        return folderList;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
