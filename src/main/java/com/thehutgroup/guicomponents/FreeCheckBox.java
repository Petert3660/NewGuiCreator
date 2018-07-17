package com.thehutgroup.guicomponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomsonp on 18/01/2017.
 */
public class FreeCheckBox extends JCheckBox {

    private Color col;

    public FreeCheckBox(Color col, int x, int y, int xSize, int ySize) {
        this.col = col;
        this.setBackground(this.col);
        this.setBounds(x, y, xSize, ySize);
        this.setText("");
        this.setBackground(this.col);
    }

    public FreeCheckBox(Color col, String label, int x, int y, int xSize, int ySize) {
        this.col = col;
        this.setBounds(x, y, xSize, ySize);
        this.setText(label);
        this.setBackground(this.col);
    }

    public String getLabelText() { return this.getText(); }
}
