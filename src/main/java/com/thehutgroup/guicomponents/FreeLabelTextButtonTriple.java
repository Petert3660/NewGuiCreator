package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;

public class FreeLabelTextButtonTriple {

    FreeLabel label;
    FreeTextField textField;
    FreeButton button;
    JPanel panel;
    Color col;

    public FreeLabelTextButtonTriple(Color col, String labelText, int x, int y, int xSize, String buttonText) {
        this.col = col;
        label = new FreeLabel(labelText, 0, 10, 240, 20);
        textField = new FreeTextField(250, 10, 200, 20);
        button = new FreeButton(buttonText, 460, 0);
        setupContainingPanel(x, y, xSize, 220);
    }

    private void setupContainingPanel(int x, int y, int xSize, int fieldSize) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(col);
        panel.setBounds(x, y, 540, 30);
        panel.add(label);
        panel.add(textField);
        panel.add(button);
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

    public String getButtonText() { return button.getButtonText(); }

    public FreeButton getButton() {
        return button;
    }
}
