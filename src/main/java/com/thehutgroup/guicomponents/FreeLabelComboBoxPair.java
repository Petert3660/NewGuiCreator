package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FreeLabelComboBoxPair {

    Color col;

    FreeLabel label;
    FreeComboBox comboBox;
    JPanel panel;
    ArrayList<String> items;

    public FreeLabelComboBoxPair(Color col, String value, int x, int y, int xSize, ArrayList<String> items) {
        this.col = col;
        this.items = items;
        label = new FreeLabel(value, 0, 0, xSize, 20);
        comboBox = new FreeComboBox(xSize + 10, 0, 200, 20, items);
        setupContainingPanel(x, y, xSize, 220);
    }

    public FreeLabelComboBoxPair(Color col, String value, int x, int y, int xSize, int fieldSize, ArrayList<String> items) {
        this.col = col;
        this.items = items;
        label = new FreeLabel(value, 0, 0, xSize, 20);
        comboBox = new FreeComboBox(xSize + 10, 0, fieldSize, 20, items);
        setupContainingPanel(x, y, xSize, fieldSize);
    }

    private void setupContainingPanel(int x, int y, int xSize, int fieldSize) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(col);
        panel.setBounds(x, y, xSize + fieldSize + 10, 20);
        panel.add(label);
        panel.add(comboBox);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void clearComboBox() {
        comboBox.clearComboBox();
    }

    public String getSelectedItem() {
        return (String) comboBox.getItem(comboBox.getSelectedIndex());
    }

    public boolean isFirstItemSelected() {

        if (comboBox.getSelectedIndex() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void repopulateComboBox(ArrayList<String> items) {
        comboBox.repopulateComboBox(items);
    }

    public String getLabelText() { return label.getText(); }

    public ArrayList<String> getItems() { return items; }
}
