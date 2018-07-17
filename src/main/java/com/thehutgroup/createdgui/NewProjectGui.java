/* This file is auto-generated by the ScriptDirectedGui program.         */
/* Please do not modify directly, but feel free to copy into a project   */

package com.thehutgroup.createdgui;

import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeLabelTextFieldPair;
import com.thehutgroup.statics.Statics;
import com.thehutgroup.utilities.FileUtilities;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NewProjectGui extends JFrame {

    private static final String SUB_HEADING_ONE = "Create New GUI Script Project";
    private static final String SUB_HEADING_TWO = "Create New Java Project";
    private static final String SUB_HEADING_THREE = "Create SpringBoot Project";
    private static final String SUB_HEADING_FOUR = "Create SpringBoot Web Project";
    private static String TITLE;
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 250;
    private Color col = new Color(230, 255, 255);

    private NewProjectGui tg = this;
    private MainGui mg;

    private String mode;

    public NewProjectGui(MainGui mg, String mode) {

        this.mode = mode;
        if (mode.equals("script")) {
            TITLE = Statics.MAIN_HEADING + " - " + SUB_HEADING_ONE;
        } else if (mode.equals("code")) {
            TITLE = Statics.MAIN_HEADING + " - " + SUB_HEADING_TWO;
        } else if (mode.equals(("spring"))) {
            TITLE = Statics.MAIN_HEADING + " - " + SUB_HEADING_THREE;
        } else if (mode.equals(("spring-web"))) {
            TITLE = Statics.MAIN_HEADING + " - " + SUB_HEADING_FOUR;
        }

        this.mg = mg;
        this.mg.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(Statics.MAIN_HEADING, 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, 180, 150, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 290, 150, 80);


        FreeLabelTextFieldPair comp0 = new FreeLabelTextFieldPair(col, "Please enter the new project name:", 30, 90, 240);

        if (mode.equals("code")) {
            comp0.updateLabelText("Please enter the new Java project name:");
        } else if (mode.equals("spring") || mode.equals("spring-web")) {
            comp0.updateLabelText("Please enter SpringBoot project name:");
        }

        comp0.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b0.doClick();
            }
        });

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mode.equals("script")) {
                    if (!StringUtils.isEmpty(comp0.getText())) {
                        File file = new File(Statics.RESOURCES_DIR + comp0.getText());
                        if (file.mkdir()) {
                            JOptionPane.showMessageDialog(tg,
                                    "Script Project: " + comp0.getText() + " has been successfully created",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            mg.updateProjectSelection(comp0.getText());
                            b1.doClick();
                        } else {
                            JOptionPane.showMessageDialog(tg, "Unable to create project: " + comp0.getText()
                                            + " , there may already be a project with this name",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            comp0.clearAndFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(tg,
                                "You must enter a new project name in order to proceed - try again!",
                                TITLE, JOptionPane.INFORMATION_MESSAGE);
                        comp0.clearAndFocus();
                    }
                } else if (mode.equals("code")) {
                    if (!StringUtils.isEmpty(comp0.getText())) {
                        File file = new File(Statics.JAVA_PROJECTS_DIR + comp0.getText());
                        if (file.mkdir()) {
                            JOptionPane.showMessageDialog(tg,
                                    "Java Project: " + comp0.getText() + " has been successfully created",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            File srcfile = new File(Statics.JAVA_PROJECTS_DIR + Statics.TEMPLATE_PROJECT);
                            try {
                                FileUtilities.copyAllFilesFromSrcDirToTargetDir(srcfile.getAbsolutePath(),
                                        file.getAbsolutePath());
                                FileUtilities.deleteFile(file.getAbsolutePath() + "\\settings.gradle");
                                FileUtilities
                                        .writeStringToFile(file.getAbsolutePath() + Statics.SETTINGS_GRADLE_LOCATION,
                                                "rootProject.name = '" + file.getName() + "'\n");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            mg.updateJavaProjectSelection(comp0.getText());
                            b1.doClick();
                        } else {
                            JOptionPane.showMessageDialog(tg, "Unable to create project: " + comp0.getText()
                                            + " , there may already be a project with this name",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            comp0.clearAndFocus();
                        }
                    }
                } else if (mode.equals("spring") || mode.equals("spring-web")) {
                    if (!StringUtils.isEmpty(comp0.getText())) {
                        File file = new File(Statics.JAVA_PROJECTS_DIR + comp0.getText());
                        if (file.mkdir()) {
                            JOptionPane.showMessageDialog(tg,
                                    "SpringBoot Project: " + comp0.getText() + " has been successfully created",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            File srcfile = null;
                            if (mode.equals("spring")) {
                                srcfile = new File(Statics.JAVA_PROJECTS_DIR + Statics.BASIC_TEMPLATE_PROJECT);
                            } else if (mode.equals("spring-web")) {
                                srcfile = new File(Statics.JAVA_PROJECTS_DIR + Statics.WEB_TEMPLATE_PROJECT);
                            }
                            try {
                                FileUtilities.copyAllFilesFromSrcDirToTargetDir(srcfile.getAbsolutePath(),
                                        file.getAbsolutePath());
                                FileUtilities.deleteFile(file.getAbsolutePath() + Statics.SETTINGS_GRADLE_LOCATION);
                                FileUtilities.writeStringToFile(
                                        file.getAbsolutePath() + Statics.SETTINGS_GRADLE_LOCATION,
                                        "rootProject.name = '" + file.getName() + "'\n");
                                FileUtilities.deleteFile(file.getAbsolutePath() + Statics.RUN_BAT_LOCATION);
                                FileUtilities.writeStringToFile(file.getAbsolutePath() + Statics.RUN_BAT_LOCATION,
                                    "cd build\\libs\n");
                                FileUtilities.appendStringToFile(file.getAbsolutePath() + Statics.RUN_BAT_LOCATION,
                                    "\n");
                                FileUtilities.appendStringToFile(file.getAbsolutePath() + Statics.RUN_BAT_LOCATION,
                                    "java -jar " + file.getName() + ".jar\n");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            // Remove .git directory to break link to remote origin

                            if (mode.equals("spring-web")) {
                                srcfile = new File(Statics.JAVA_PROJECTS_DIR + comp0.getText());
                                File[] files = srcfile.listFiles();

                                for (File targfile : files) {
                                    if (targfile.getName().equals(".git") && targfile.isDirectory()) {
                                        try {
                                            FileUtilities.deleteDirectory(targfile);
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            }

                            mg.updateJavaProjectSelection(comp0.getText());
                            b1.doClick();
                        } else {
                            JOptionPane.showMessageDialog(tg, "Unable to create project: " + comp0.getText()
                                            + " , there may already be a project with this name",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                            comp0.clearAndFocus();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(tg,
                            "You must enter a new project name in order to proceed - try again!",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    comp0.clearAndFocus();
                }
            }
        });

        // This is the control for the Cancel-implement button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mg.setEnabled(true);
                NewProjectGui.this.tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(comp0.getPanel());
        p1.add(l0);
        this.add(p1);

    }
}
