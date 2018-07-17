package com.thehutgroup.guicomponents;

import javax.swing.*;

/**
 * Created by thomsonp on 05/01/2017.
 */
public class FreeTextField extends JTextField {

    public FreeTextField(int x, int y, int xSize, int ySize) {
        this.setBounds(x, y, xSize, ySize);
    }

    public FreeTextField(String value, int x, int y, int xSize, int ySize) {
        this.setBounds(x, y, xSize, ySize);
        this.setText(value);
    }

    public void clearTextField() {
        this.setText("");
    }

    public void focusTextField() {
        this.grabFocus();
    }

    public void clearAndFocus() {
        clearTextField();
        focusTextField();
    }

    public boolean empty() {
        if (this.getText().equals("")) {
            return true;
        }
        return false;
    }

    public String getValue() {return this.getText(); }
}
