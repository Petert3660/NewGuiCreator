package com.thehutgroup.guiScriptParser;

import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeCheckBox;
import com.thehutgroup.guicomponents.FreeComboBox;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeLabelComboBoxPair;
import com.thehutgroup.guicomponents.FreeLabelTextButtonTriple;
import com.thehutgroup.guicomponents.FreeLabelTextFieldPair;
import com.thehutgroup.guicomponents.FreeRadioButton;
import com.thehutgroup.guicomponents.FreeRadioButtonGroup;
import com.thehutgroup.guicomponents.FreeTextArea;
import com.thehutgroup.guicomponents.FreeTextField;
import com.thehutgroup.guis.GuiProperties;
import com.thehutgroup.messages.MessageHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class GuiBuilder {

    private GuiProperties guiProperties;

    private MessageHandler messageHandler;
    
    @Autowired
    public GuiBuilder(GuiProperties guiProperties, MessageHandler messageHandler) {
        this.guiProperties = guiProperties;
        this.messageHandler = messageHandler;
    }

    public void buildGuiClass() {

        BufferedWriter bw = null;
        FileWriter fw = null;

        int itemsIndex = 0;

        try {

            File file = new File(messageHandler.getMessage("filepaths.scd.resourcedir", new String[]{"TestGui.java.tmp"}));
            if (file.exists()) {
                file.delete();
            }

            fw = new FileWriter(messageHandler.getMessage("filepaths.scd.resourcedir", new String[]{"TestGui.java.tmp"}), true);
            bw = new BufferedWriter(fw);

            bw.write("/* This file is auto-generated by the ScriptDirectedGui program.                  */\n");
            bw.write("/* Please do not modify directly as the code cannot then be guaranteed to operate */\n");
            bw.write("/* correctly. However, please feel free to copy the source into a project.        */\n\n");

            bw.write("package com.thehutgroup.testgui;\n\n");
            bw.write("import javax.swing.*;\n");
            bw.write("import java.awt.*;\n");
            bw.write("import java.util.ArrayList;\n");
            bw.write("import java.io.File;\n");
            bw.write("import com.thehutgroup.guicomponents.*;\n");
            bw.write("import java.awt.event.ActionEvent;\n");
            bw.write("import java.awt.event.ActionListener;\n\n");
            bw.write("public class TestGui extends JFrame {\n\n");

            bw.write("    private static final String MAIN_HEADING = \"" + guiProperties.getMainHeading() + "\";\n");
            if (!StringUtils.isEmpty(guiProperties.getSubHeading())) {
                bw.write("    private static final String SUB_HEADING = \"" + guiProperties.getSubHeading() + "\";\n");
                bw.write("    private static final String TITLE = MAIN_HEADING + \" - \" + SUB_HEADING;\n");
            } else {
                bw.write("    private static final String TITLE = MAIN_HEADING;\n");
            }
            bw.write("    private static final int FRAME_X_SIZE = " +  guiProperties.getFrameXSize() + ";\n");
            bw.write("    private static final int FRAME_Y_SIZE = " +  guiProperties.getFrameYSize() + ";\n");

            bw.write("    private Color col = new Color(" + guiProperties.getGuiBackgroundColor().getRed() + ", "
                    + guiProperties.getGuiBackgroundColor().getGreen() + ", "
                    + guiProperties.getGuiBackgroundColor().getBlue() + ");\n\n");

            bw.write("    private TestGui tg = this;\n\n");

            if (guiProperties.getMenues().size() > 0) {
                bw.write("    private JMenuBar menuBar = new JMenuBar();\n\n");
            }

            bw.write("    public TestGui() {\n");
            bw.write("        this.setTitle(TITLE);\n");
            bw.write("        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);\n\n");

            bw.write("        JPanel p1 = new JPanel();\n");
            bw.write("        p1.setLayout(null);\n");
            bw.write("        p1.setBackground(col);\n\n");

            bw.write("        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font(\"\", Font.BOLD + Font.ITALIC, 20));\n\n");

            //Add the declaration of the buttons
            String button;
            int xStart;
            if (guiProperties.getButtons().size() % 2 == 0) {
                int multiplier = (guiProperties.getButtons().size() / 2) - 1;
                xStart = guiProperties.getFrameXSize()/2 - 95 - (110 * multiplier);
            } else {
                int multiplier = guiProperties.getButtons().size() / 2;
                xStart = guiProperties.getFrameXSize()/2 - 40 - (110 * multiplier);
            }
            int yFixed = guiProperties.getFrameYSize() - 100;
            for (int i = 0; i < guiProperties.getButtons().size(); i++) {
                button = "b" + String.valueOf(i);
                if (((FreeButton) guiProperties.getButtons().get(i)).getButtonText().contains("-implement")) {
                    String buttonText = ((FreeButton) guiProperties.getButtons().get(i)).getButtonText().replace("-implement", "");
                    bw.write("        FreeButton " + button + " = new FreeButton("
                            + "\"" + buttonText
                            + "\", " + xStart + ", " + yFixed + ", 80);\n\n");
                } else {
                    bw.write("        FreeButton " + button + " = new FreeButton("
                            + "\"" + ((FreeButton) guiProperties.getButtons().get(i)).getButtonText()
                            + "\", " + xStart + ", " + yFixed + ", 80);\n\n");
                }
                xStart = xStart + 110;
            }

            // Add declaration of radio buttons
            String radios;
            int startX = 30;
            for (int i = 0; i < guiProperties.getRadioButtons().size(); i++) {
                radios = "rb" + String.valueOf(i);
                bw.write("        FreeRadioButton " + radios + " = new FreeRadioButton("
                         + "col, \""
                         + ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getLabelText() + "\", "
                         + startX + ", "
                         + (int) ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getBounds().getY() + ", "
                         + ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getWidth() + ", "
                         + ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getHeight()
                         + ");\n");
                if (i == 0) {
                    bw.write("        " + radios + ".setSelected();\n");
                }
                startX = startX + ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getWidth() + 30;
            }
            bw.write("\n");

            // Add the declaration of the components
            String component;
            for (int i = 0; i < guiProperties.getComponents().size(); i++) {
                component = "comp" + String.valueOf(i);
                if (guiProperties.getComponents().get(i) instanceof FreeLabelTextFieldPair) {
                    bw.write("        FreeLabelTextFieldPair " + component + " = new FreeLabelTextFieldPair(" +
                            "col, " +
                            "\"" + ((FreeLabelTextFieldPair) guiProperties.getComponents().get(i)).getLabelText()
                            + "\", "
                            + (int) ((FreeLabelTextFieldPair) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getX() + ", "
                            + (int) ((FreeLabelTextFieldPair) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getY() + ", "
                            + ((FreeLabelTextFieldPair) guiProperties.getComponents().get(i)).getPanel().getBounds()
                            .getSize().width + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeLabelTextButtonTriple) {
                    bw.write("        FreeLabelTextButtonTriple " + component + " = new FreeLabelTextButtonTriple(" +
                            "col, " +
                            "\"" + ((FreeLabelTextButtonTriple) guiProperties.getComponents().get(i)).getLabelText()
                            + "\", "
                            + (int) ((FreeLabelTextButtonTriple) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getX() + ", "
                            + (int) ((FreeLabelTextButtonTriple) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getY() + ", "
                            + "10, "
                            + "\"" + ((FreeLabelTextButtonTriple) guiProperties.getComponents().get(i)).getButtonText()
                            + "\");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeLabelComboBoxPair) {
                    String items = "items" + String.valueOf(itemsIndex++);
                    bw.write("        ArrayList<String> " + items + " = new ArrayList<String>();\n");
                    for (int j = 0; j < ((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getComboBox().getItemCount(); j++) {
                        bw.write("        " + items + ".add(\"" + ((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getComboBox().getItemAt(j) + "\");\n");
                    }
                    bw.write("        FreeLabelComboBoxPair " + component + " = new FreeLabelComboBoxPair(" +
                            "col, " +
                            "\"" + ((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getLabelText() + "\", "
                            + (int)((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getPanel().getBounds().getX() + ", "
                            + (int)((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getPanel().getBounds().getY() + ", "
                            + ((FreeLabelComboBoxPair) guiProperties.getComponents().get(i)).getPanel().getBounds().getSize().width + ", " + items + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeTextArea) {
                    bw.write("        FreeTextArea " + component + " = new FreeTextArea(" +
                            "col, " +
                            "\"" + ((FreeTextArea) guiProperties.getComponents().get(i)).getLabelText()
                            + "\", "
                            + (int) ((FreeTextArea) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getX() + ", "
                            + (int) ((FreeTextArea) guiProperties.getComponents().get(i)).getPanel()
                            .getBounds().getY() + ", "
                            + ((FreeTextArea) guiProperties.getComponents().get(i)).getXSize() + ", "
                            + ((FreeTextArea) guiProperties.getComponents().get(i)).getPanel().getBounds()
                            .getSize().width + ", "
                            + ((FreeTextArea) guiProperties.getComponents().get(i)).getPanel().getBounds()
                            .getSize().height + ", "
                            + ((FreeTextArea) guiProperties.getComponents().get(i)).getReadOnly()
                            + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeCheckBox) {
                    bw.write("        FreeCheckBox " + component + " = new FreeCheckBox(" +
                             "col, " +
                             "\"" + ((FreeCheckBox) guiProperties.getComponents().get(i)).getLabelText()
                             + "\", "
                             + (int) ((FreeCheckBox) guiProperties.getComponents().get(i)).getBounds().getX() + ", "
                             + (int) ((FreeCheckBox) guiProperties.getComponents().get(i)).getBounds().getY() + ", "
                             + ((FreeCheckBox) guiProperties.getComponents().get(i)).getBounds()
                             .getSize().width + ", "
                             + ((FreeCheckBox) guiProperties.getComponents().get(i)).getBounds()
                             .getSize().height
                             + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeLabel) {
                    bw.write("        FreeLabel " + component + " = new FreeLabel(" +
                            "\"" + ((FreeLabel) guiProperties.getComponents().get(i)).getLabelText()
                            + "\", "
                            + (int) ((FreeLabel) guiProperties.getComponents().get(i)).getBounds().getX() + ", "
                            + (int) ((FreeLabel) guiProperties.getComponents().get(i)).getBounds().getY() + ", "
                            + ((FreeLabel) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().width + ", "
                            + ((FreeLabel) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().height
                            + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeTextField) {
                    bw.write("        FreeTextField " + component + " = new FreeTextField(" +
                            "\"" + ((FreeTextField) guiProperties.getComponents().get(i)).getValue()
                            + "\", "
                            + (int) ((FreeTextField) guiProperties.getComponents().get(i)).getBounds().getX() + ", "
                            + (int) ((FreeTextField) guiProperties.getComponents().get(i)).getBounds().getY() + ", "
                            + ((FreeTextField) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().width + ", "
                            + ((FreeTextField) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().height
                            + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeComboBox) {
                    String items = "items" + String.valueOf(itemsIndex++);
                    bw.write("        ArrayList<String> " + items + " = new ArrayList<String>();\n");
                    for (int j = 0; j < ((FreeComboBox) guiProperties.getComponents().get(i)).getItemCount(); j++) {
                        bw.write("        " + items + ".add(\"" + ((FreeComboBox) guiProperties.getComponents().get(i)).getItemAt(j) + "\");\n");
                    }
                    bw.write("        FreeComboBox " + component + " = new FreeComboBox("
                            + (int) ((FreeComboBox) guiProperties.getComponents().get(i)).getBounds().getX() + ", "
                            + (int) ((FreeComboBox) guiProperties.getComponents().get(i)).getBounds().getY() + ", "
                            + ((FreeComboBox) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().width + ", "
                            + ((FreeComboBox) guiProperties.getComponents().get(i)).getBounds()
                            .getSize().height
                            + ", " + items + ");\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeRadioButtonGroup) {
                    bw.write("        FreeRadioButtonGroup " + component + " = new FreeRadioButtonGroup();\n");
                    String items = "items" + String.valueOf(itemsIndex++);
                    bw.write("        ArrayList<FreeRadioButton> " + items + " = new ArrayList<FreeRadioButton>();\n");
                    for (int j = 0; j < guiProperties.getRadioButtons().size(); j++) {
                        radios = "rb" + String.valueOf(j);
                        bw.write("        " + items + ".add(" + radios + ");\n");
                    }
                    bw.write(("        " + component + ".addButtons(" + items + ");\n\n"));
                }
            }

            // Add the action handlers for the buttons
            for (int i = 0; i < guiProperties.getButtons().size(); i++) {
                button = "b" + String.valueOf(i);
                bw.write("        // This is the control for the " + ((FreeButton) guiProperties.getButtons().get(i)).getButtonText() + " button\n");
                bw.write("        " + button + ".addActionListener(new ActionListener() {\n");
                bw.write("            public void actionPerformed(ActionEvent e) {\n");
                if (((FreeButton) guiProperties.getButtons().get(i)).getButtonText().equals("Exit-implement")) {
                    bw.write("                tg.dispose();\n");
                } else if (((FreeButton) guiProperties.getButtons().get(i)).getButtonText().equals("Browse-implement")) {
                    bw.write("                JFileChooser fc = new JFileChooser();\n");
                    bw.write("                fc.setCurrentDirectory(new File(\"c:\\\\\"));\n");
                    bw.write("                int returnVal = fc.showOpenDialog(tg);\n");
                } else if (((FreeButton) guiProperties.getButtons().get(i)).getButtonText().equals("Cancel-implement")) {
                    bw.write("                tg.dispose();\n");
                } else {
                    bw.write("                System.out.println(\"Button output - \" + " + button
                            + ".getButtonText());\n");
                }
                bw.write("            }\n");
                bw.write("        });\n\n");
            }

            // Add the handlers for check boxes
            for (int i = 0; i < guiProperties.getComponents().size(); i++) {
                if (guiProperties.getComponents().get(i) instanceof FreeCheckBox) {
                    component = "comp" + String.valueOf(i);
                    bw.write("        // This is the control for the " + ((FreeCheckBox) guiProperties
                            .getComponents().get(i)).getLabelText() + " check box\n");
                    bw.write("        " + component + ".addActionListener(new ActionListener() {\n");
                    bw.write("            public void actionPerformed(ActionEvent e) {\n");
                    bw.write("                if (" + component + ".isSelected()) {\n");
                    bw.write("                    System.out.println(\"Checkbox - \" + " + component
                            + ".getLabel() + \" has been selected\");\n");
                    bw.write("                } else {\n");
                    bw.write("                    System.out.println(\"Checkbox - \" + " + component
                            + ".getLabel() + \" has been de-selected\");\n");
                    bw.write("                }\n");
                    bw.write("            }\n");
                    bw.write("        });\n\n");
                } else if (guiProperties.getComponents().get(i) instanceof FreeLabelTextButtonTriple) {
                    component = "comp" + String.valueOf(i);
                    bw.write("        // This is the control for the " + ((FreeLabelTextButtonTriple) guiProperties
                            .getComponents().get(i)).getButtonText() + " button on the triple Label, Text, Button\n");
                    bw.write("        " + component + ".getButton().addActionListener(new ActionListener() {\n");
                    bw.write("            public void actionPerformed(ActionEvent e) {\n");
                    bw.write("                System.out.println(\"Button - \" + " + component
                            + ".getButtonText() + \" in the triple group has been clicked\");\n");
                    bw.write("            }\n");
                    bw.write("        });\n\n");
                }
            }

            // Add the handlers for the radio buttons
            for (int i = 0; i < guiProperties.getRadioButtons().size(); i++) {
                radios = "rb" + String.valueOf(i);
                bw.write("        // This is the control for the " + ((FreeRadioButton) guiProperties.getRadioButtons().get(i)).getLabelText() + " radio button\n");
                bw.write("        " + radios + ".addActionListener(new ActionListener() {\n");
                bw.write("            public void actionPerformed(ActionEvent e) {\n");
                bw.write("                System.out.println(\"Radio button output - \" + " + radios
                            + ".getLabelText());\n");
                bw.write("            }\n");
                bw.write("        });\n\n");
            }

            // Add buttons to the main panel and add to frame
            for (int i = 0; i < guiProperties.getButtons().size(); i++) {
                button = "b" + String.valueOf(i);
                bw.write("        p1.add(" + button + ");\n");
            }

            // Add radio buttons to main panel and frame
            for (int i = 0; i < guiProperties.getRadioButtons().size(); i++) {
                radios = "rb" + String.valueOf(i);
                bw.write("        p1.add(" + radios + ");\n");
            }

            // Add components to the main panel and add to frame
            for (int i = 0; i < guiProperties.getComponents().size(); i++) {
                component = "comp" + String.valueOf(i);
                if (!(guiProperties.getComponents().get(i) instanceof FreeRadioButtonGroup)) {
                    if (guiProperties.getPanelComponent().get(i)) {
                        bw.write("        p1.add(" + component + ".getPanel());\n");
                    } else {
                        bw.write("        p1.add(" + component + ");\n");
                    }
                }
            }

            bw.write("        p1.add(l0);\n");
            if (guiProperties.getMenues().size() > 0) {
                bw.write("\n        setUpMenuBar();\n");
                bw.write("          this.setJMenuBar(menuBar);\n");
            }
            bw.write("        this.add(p1);\n\n");
            bw.write("    }\n");
            if (guiProperties.getMenues().size() > 0) {
                createMenuMethod(bw);
            }
            bw.write("}\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

    private void createMenuMethod(BufferedWriter bw) throws IOException {

        String menuDesignation;
        String menuItemDesignation;
        int menu = 0;
        int menuItem = 0;

        bw.write("\n");
        bw.write("    private void setUpMenuBar() {\n");
        for (int i = 0; i < guiProperties.getMenues().size(); i++) {
            String input = guiProperties.getMenues().get(i).replace("[", "").replace("]", "").replace("    ", "");
            String[] menuName = input.split(": ");
            String actualMenuName = menuName[0];
            String[] menuItems = menuName[1].split(", ");
            menuDesignation = "menu" + String.valueOf(menu);
            bw.write("        JMenu " + menuDesignation + " = new JMenu(\"" + actualMenuName + "\");\n");
            for (int j = 0; j < menuItems.length; j++) {
                menuItemDesignation = "menuItem" + String.valueOf(menu) + String.valueOf(menuItem++);
                if (!menuItems[j].equals("-separator")) {
                    if (!menuItems[j].equals("Exit-implement")) {
                        bw.write("        JMenuItem " + menuItemDesignation + " = new JMenuItem(\"" + menuItems[j]
                                + "\");\n");
                    } else {
                        String temp = menuItems[j].replace("-implement", "");
                        bw.write("        JMenuItem " + menuItemDesignation + " = new JMenuItem(\"" + temp
                                + "\");\n");
                    }
                    bw.write("        " + menuDesignation + ".add(" + menuItemDesignation + ");\n");
                } else {
                    bw.write("        " + menuDesignation + ".addSeparator();\n");
                }
            }

            bw.write("\n");
            menuItem = 0;
            for (int j = 0; j < menuItems.length; j++) {
                menuItemDesignation = "menuItem" + String.valueOf(menu) + String.valueOf(menuItem++);
                if (!menuItems[j].equals("-separator")) {
                    bw.write("        // This is the control for the " + actualMenuName + "/" + menuItems[j] + " menu item\n");
                    bw.write("        " + menuItemDesignation + ".addActionListener(new ActionListener() {\n");
                    bw.write("            public void actionPerformed(ActionEvent e) {\n");
                    if (menuItems[j].equals("Exit-implement")) {
                        bw.write("                tg.dispose();\n");
                    } else {
                        bw.write("                System.out.println(\"Menu item - " + menuItems[j] + " in the "
                                + actualMenuName + " menu has been clicked\");\n");
                    }
                    bw.write("            }\n");
                    bw.write("        });\n\n");
                }
            }

            menu++;
            menuItem = 0;
            bw.write("        menuBar.add(" + menuDesignation + ");\n");
            bw.write("\n");
        }

        bw.write("    }\n\n");
    }
}
