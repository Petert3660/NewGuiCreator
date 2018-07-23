package com.thehutgroup.guicomponents;

import javax.swing.JButton;

/**
 * Created by thomsonp on 05/01/2017.
 */
public class FreeButton extends JButton {

    // Regular button values
    public static final String OK = "OK";
    public static final String EXIT = "Exit";
    public static final String CANCEL = "Cancel";
    public static final String CLOSE = "Close";
    public static final String BROWSE = "Browse";
    public static final String CLEAR = "Clear";

    public FreeButton(String value, int x, int y) {
        this.setText(value);
        this.setBounds(x, y, 80, 30);
    }

    public FreeButton(String value, int x, int y, int size) {
        this.setText(value);
        this.setBounds(x, y, size, 30);
    }

    public void alter(String value, int x, int y, int newSize) {
        this.setText(value);
        this.setBounds(x, y, newSize, 30);
    }

    public String getButtonText() { return this.getText(); }
}
