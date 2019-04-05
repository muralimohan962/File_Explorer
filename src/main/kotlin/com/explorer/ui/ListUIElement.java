package com.explorer.ui;

import javax.swing.*;

public class ListUIElement {
    private JLabel iconLabel;
    private JLabel textLabel;
    private JPanel rootPanel;

    public ListUIElement() {
        iconLabel.setIcon(new ImageIcon("/home/jetbrains/IdeaProjects/File Manager/src/main/resources/folder_icon.png"));
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public JLabel getTextLabel() {
        return textLabel;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
