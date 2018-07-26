package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.util.ArrayList;

public class FreeComboBox extends JComboBox {

    public FreeComboBox(int x, int y, int xSize, int ySize) {
        this.setBounds(x, y, xSize, ySize);
        this.addItem("--Select");
    }

    public FreeComboBox(int x, int y, int xSize, int ySize, ArrayList<String> items) {
            this.setBounds(x, y, xSize, ySize);
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
            return getItem(this.getSelectedIndex());
        }

        public void clearComboBox() {
            this.setSelectedIndex(0);
        }

        public void repopulateComboBox(ArrayList<String> items) {
            this.removeAllItems();
            for (String item : items) {
                this.addItem(item);
        }
    }
}
