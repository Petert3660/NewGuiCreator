/* This file is auto-generated by the ScriptDirectedGui program.         */
/* Please do not modify directly, but feel free to copy into a project   */

package com.thehutgroup.createdgui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import com.thehutgroup.guicomponents.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestGui extends JFrame {

    private static final String MAIN_HEADING = "GIT Branch Controller";
    private static final String SUB_HEADING = "Create New Branch in GIT";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 300;
    private Color col = new Color(230, 255, 255);

    private TestGui tg = this;

    public TestGui() {
        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("OK", 180, 200, 80);

        FreeButton b1 = new FreeButton("Cancel", 290, 200, 80);


        ArrayList<String> items0 = new ArrayList<String>();
        FreeLabelComboBoxPair comp0 = new FreeLabelComboBoxPair(col, "Please select the project name:", 30, 90, 240, items0);

        FreeLabelTextFieldPair comp1 = new FreeLabelTextFieldPair(col, "Please enter the new branch name:", 30, 140, 240);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button output - " + b0.getButtonText());
            }
        });

        // This is the control for the Cancel-implement button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(comp0.getPanel());
        p1.add(comp1.getPanel());
        p1.add(l0);
        this.add(p1);

    }
}
