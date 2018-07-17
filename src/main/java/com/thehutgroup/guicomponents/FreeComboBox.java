package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.util.ArrayList;

public class FreeComboBox extends JComboBox {

    private ArrayList<String> items = new ArrayList<String>();

    public FreeComboBox(int x, int y, int xSize, int ySize) {
        this.setBounds(x, y, xSize, ySize);
        this.addItem("--Select");
    }

    public FreeComboBox(int x, int y, int xSize, int ySize, ArrayList<String> items) {
        this.items = items;
        this.setBounds(x, y, xSize, ySize);
        this.addItem("--Select");
        if (items != null) {
            for (String item : items) {
                this.addItem(item);
            }
        }
    }

    public void focusComboBox() {
        this.grabFocus();
    }

    public String getItem(int index) {
        return (String) this.getItemAt(index);
    }

    public String getSelectedItem() {
        return (String) getItem(this.getSelectedIndex());
    }

    public void clearComboBox() {
        this.setSelectedIndex(0);
    }

    public void repopulateComboBox(ArrayList<String> items) {
        this.removeAllItems();
        this.addItem("--Select");
        for (String item : items) {
            this.addItem(item);
        }
    }

    public ArrayList<String> getItems() { return items; }
}
