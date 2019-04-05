package com.explorer.ui;

import javax.swing.*;

public class SearchComponent {
    private JPanel rootPanel;
    private JTextField nameField;
    private JTextField extensionField;
    private JButton searchButton;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public  String getFileName() {
        return nameField.getText();
    }

    public String getFileExtension() {
        return extensionField.getText();
    }
}
