package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomsonp on 18/01/2017.
 */
public class FreeRadioButton extends JRadioButton {

    Color col;

    public FreeRadioButton(Color col, int x, int y, int xSize, int ySize) {
        this.col = col;
        this.setBackground(this.col);
        this.setBounds(x, y, xSize, ySize);
        this.setText("");
    }

    public FreeRadioButton(Color col, String label, int x, int y, int xSize, int ySize) {
        this.col = col;
        this.setBackground(this.col);
        this.setBounds(x, y, xSize, ySize);
        this.setText(label);
    }

    public void setSelected() {
        this.setSelected(true);
    }

    public String getLabelText() { return this.getLabel(); }
}
