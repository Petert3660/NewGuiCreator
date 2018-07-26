/* This file is auto-generated by the ScriptDirectedGui program.         */
/* Please do not modify directly, but feel free to copy into a project   */

package com.thehutgroup.createdgui;

import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.statics.Statics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame {

    private static final String SUB_HEADING = "About";
    private static final String TITLE = SUB_HEADING;
    private static final int FRAME_X_SIZE = 400;
    private static final int FRAME_Y_SIZE = 300;
    private Color col = new Color(230, 255, 255);

    private About tg = this;

    private MainGui mg;

    public About(MainGui mg) {

        this.mg = mg;
        this.mg.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(Statics.MAIN_HEADING, 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, 160, 200, 80);


        FreeLabel comp0 = new FreeLabel(Statics.MAIN_HEADING, 30, 90, 200, 20);

        FreeLabel comp1 = new FreeLabel(Statics.VERSION + BuildVersion.getBuildVersion(), 30, 110, 200, 20);

        FreeLabel comp2 = new FreeLabel(Statics.COPYRIGHT, 30, 130, 200, 20);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mg.setEnabled(true);
                tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(comp0);
        p1.add(comp1);
        p1.add(comp2);
        p1.add(l0);
        this.add(p1);
    }
}
