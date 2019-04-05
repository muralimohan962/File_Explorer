package com.explorer.ui;



import com.explorer.virtualFile.VirtualFile;

import javax.swing.*;

public class ListComponent {
    private JList<VirtualFile> list;
    private JPanel rootPanel;


    public JList<VirtualFile> getList() {
        return list;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
