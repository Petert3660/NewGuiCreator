package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomsonp on 12/01/2017.
 */
public class FreeTextArea {

    Color col;

    JPanel panel;
    JTextArea jta;
    JScrollPane jsp;
    JLabel label;
    boolean readOnly = false;
    int xSize;

    public FreeTextArea(Color col, String value, int x, int y, int xSize, int width, int height, boolean readOnly) {
        this.col = col;
        this.xSize = xSize;
        label = new FreeLabel(value, 0, 0, xSize, 20);
        jta = new JTextArea();
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        jsp = new JScrollPane(jta);
        jsp.setBounds(0, 20, width, height);
        setupContainingPanel(x, y, width, height);
        this.readOnly = readOnly;
        if (readOnly) {
            jta.setEditable(false);
        }
    }

    private void setupContainingPanel(int x, int y, int areaWidth, int areaHeight) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(col);
        panel.setBounds(x, y, areaWidth, areaHeight + 20);
        panel.add(label);
        panel.add(jsp);
    }

    public boolean empty() {
        if (jta.getText().equals("")) {
            return true;
        }
        return false;
    }

    public void append(String input) {
        if (readOnly) {
            getTextArea().setEditable(true);
            getTextArea().append(input);
            getTextArea().setEnabled(false);
        } else {
            getTextArea().append(input);
        }
    }

    public void appendNewLine(String input) {
        if (readOnly) {
            getTextArea().setEditable(true);
            getTextArea().append(input + "\n");
            getTextArea().setEnabled(false);
        } else {
            getTextArea().append(input + "\n");
        }
    }

    public void updateVerticalScrolling() {
        JScrollBar vertical = jsp.getVerticalScrollBar();
        int max = vertical.getMaximum();
        vertical.setValue(max);
    }

    public String getText() {
        return this.getTextArea().getText();
    }

    public void setText(String input) {
        if (readOnly) {
            getTextArea().setEditable(true);
            this.getTextArea().setText(input);
            getTextArea().setEnabled(false);
        } else {
            this.getTextArea().setText(input);
        }
    }

    public void clearTextArea() {
        if (readOnly) {
            getTextArea().setEditable(true);
            getTextArea().setText("");
            getTextArea().setEnabled(false);
        } else {
            getTextArea().setText("");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getTextArea() {
        return jta;
    }

    public JScrollPane getScrollPane() { return jsp; }

    public String getLabelText() { return label.getText(); }

    public void setLabelText(String newValue) {  label.setText(newValue); }

    public boolean getReadOnly() { return readOnly; }

    public int getXSize() { return xSize; }
}
