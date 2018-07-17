package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomsonp on 05/01/2017.
 */
public class FreeLabelTextFieldPair {

    FreeLabel label;
    FreeTextField textField;
    JPanel panel;
    Color col;

    public FreeLabelTextFieldPair(Color col, String value, int x, int y, int xSize) {
        this.col = col;
        label = new FreeLabel(value, 0, 0, xSize, 20);
        textField = new FreeTextField(xSize + 10, 0, 200, 20);
        setupContainingPanel(x, y, xSize, 220);
    }

    public FreeLabelTextFieldPair(Color col, String value, int x, int y, int xSize, int fieldSize) {
        this.col = col;
        label = new FreeLabel(value, 0, 0, xSize, 20);
        textField = new FreeTextField(xSize + 10, 0, fieldSize, 20);
        setupContainingPanel(x, y, xSize, fieldSize);
    }

    private void setupContainingPanel(int x, int y, int xSize, int fieldSize) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(col);
        panel.setBounds(x, y, xSize + fieldSize + 10, 20);
        panel.add(label);
        panel.add(textField);
    }

    public JPanel getPanel() {
        return panel;
    }

    public FreeTextField getTextField() {
        return textField;
    }

    public void clearTextField() {
        textField.clearTextField();
    }

    public void focusTextField() {
        textField.focusTextField();
    }

    public void clearAndFocus() {
        clearTextField();
        focusTextField();
    }

    public boolean empty() {
        if (textField.getText().equals("")) {
            return true;
        }
        return false;
    }

    public String getText() {
        return this.getTextField().getText();
    }

    public void setText(String input) {
        this.getTextField().setText(input);
    }

    public void updateLabelText(String input) { label.setText(input); }

    public String getLabelText() { return label.getText(); }
}
