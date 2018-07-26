package com.thehutgroup.createdgui;

import com.thehutgroup.exceptions.GuiScriptFileException;
import com.thehutgroup.guiScriptParser.GuiBuilder;
import com.thehutgroup.guiScriptParser.GuiScriptParser;
import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeTextArea;
import com.thehutgroup.guis.GuiHelper;
import com.thehutgroup.guis.GuiProperties;
import com.thehutgroup.messages.MessageHandler;
import com.thehutgroup.runners.ScriptRunner;
import com.thehutgroup.statics.ComponentConstants;
import com.thehutgroup.statics.InfoMessages;
import com.thehutgroup.statics.Statics;
import com.thehutgroup.statics.WarningMessages;
import com.thehutgroup.utilities.FileUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

public class MainGui extends JFrame {

    private static final String TITLE = Statics.MAIN_HEADING;
    private static final int FRAME_X_SIZE = 1000;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(235, 255, 255);

    private MainGui tg = this;

    private JMenuBar menuBar = new JMenuBar();

    private JPanel p1 = new JPanel();

    private FreeTextArea comp0 = new FreeTextArea(col, ComponentConstants.TEXTAREA_LABEL, 30, 90, 300, 935, 620, false);

    private FreeLabel comp1 = new FreeLabel(Statics.PROJECT_SEL_MESSAGE + Statics.NO_PROJ_MESSAGE, 30, 750, 400, 20);
    private FreeLabel comp2 = new FreeLabel(Statics.LAST_FILE_BUILT_MESSAGE, 30, 790, 400, 20);
    private FreeLabel comp3 = new FreeLabel(Statics.JAVA_PROJ_SEL_MESSAGE + Statics.NO_PROJ_MESSAGE, 30, 770, 400, 20);

    JMenuItem menuItem50;

    private String projectName = "";
    private String javaProjectName = "";
    private String comboOptionsFile = "";
    private File testFile;

    private GuiProperties guiProperties;
    private MessageHandler messageHandler;

    @Autowired
    public MainGui(GuiProperties guiProperties, MessageHandler messageHandler) {

        this.guiProperties = guiProperties;
        this.messageHandler = messageHandler;

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(Statics.MAIN_HEADING, 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.EXIT, 460, 800, 80);

        // This is the control for the Exit-implement button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(Statics.EXIT_STATUS);
            }
        });

        p1.add(b0);
        p1.add(comp0.getPanel());
        p1.add(l0);
        p1.add(comp1);
        p1.add(comp2);
        p1.add(comp3);

        setUpMenuBar();
          this.setJMenuBar(menuBar);
        this.add(p1);
    }

    private void setUpMenuBar() {
        JMenu menu0 = new JMenu(messageHandler.getMessage("menu.titles.file"));
        JMenuItem menuItem00 = new JMenuItem(messageHandler.getMessage("menu.titles.file.open.guiscript"));
        menu0.add(menuItem00);
        JMenuItem menuItem01 = new JMenuItem(messageHandler.getMessage("menu.titles.file.save.guiscript"));
        menu0.add(menuItem01);
        JMenuItem menuItem02 = new JMenuItem(messageHandler.getMessage("menu.titles.file.close.guiscript"));
        menu0.add(menuItem02);
        menu0.addSeparator();
        JMenuItem menuItem05 = new JMenuItem(messageHandler.getMessage("menu.titles.file.open.combooption"));
        menu0.add(menuItem05);
        JMenuItem menuItem06 = new JMenuItem(messageHandler.getMessage("menu.titles.file.save.combooption"));
        menu0.add(menuItem06);
        JMenuItem menuItem07 = new JMenuItem(messageHandler.getMessage("menu.titles.file.close.combooption"));
        menu0.add(menuItem07);
        menu0.addSeparator();
        JMenuItem menuItem04 = new JMenuItem(messageHandler.getMessage("menu.titles.file.exit"));
        menu0.add(menuItem04);

        // This is the control for the File\Open File menu item
        menuItem00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    try {
                        if (openOpenFileChoice("GUI Files", Statics.GUI_EXTENSION) == 0) {
                            JOptionPane.showMessageDialog(tg, WarningMessages.NO_GUI_FILES,
                                    TITLE, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, WarningMessages.NO_PROJECT_SELECTED,
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // This is the control for the File\Save File menu item
        menuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(projectName)) {
                    JOptionPane.showMessageDialog(tg, WarningMessages.NO_PROJECT_SELECTED,
                        TITLE, JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        openSaveFileChoice(Statics.GUI_EXTENSION);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // This is the control for the File\Close File menu item
        menuItem02.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.clearTextArea();
            }
        });

        // This is the control for the File\Exit-implement menu item
        menuItem04.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(Statics.EXIT_STATUS);
            }
        });

        menuItem05.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    try {
                        if (openOpenFileChoice(Statics.COMBO_DESC, Statics.COMBO_OPTIONS_EXTENSION) == 0) {
                            JOptionPane.showMessageDialog(tg, WarningMessages.NO_COMBO_FILES,
                                    TITLE, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, WarningMessages.NO_PROJECT_SELECTED,
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        menuItem06.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(projectName)) {
                    JOptionPane.showMessageDialog(tg, WarningMessages.NO_PROJECT_SELECTED,
                        TITLE, JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        openSaveFileChoice(Statics.COMBO_OPTIONS_EXTENSION);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        menuItem07.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.clearTextArea();
            }
        });

        menuBar.add(menu0);

        JMenu menu1 = new JMenu(messageHandler.getMessage("menu.titles.project"));
        JMenuItem menuItem10 = new JMenuItem(messageHandler.getMessage("menu.titles.project.create"));
        menu1.add(menuItem10);
        JMenuItem menuItem11 = new JMenuItem(messageHandler.getMessage("menu.titles.project.open"));
        menu1.add(menuItem11);

        // This is the control for the Create New Project\Projects menu item
        menuItem10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "script");
                GuiHelper.showFrame(npg);
            }
        });

        // This is the control for the Open Existing Project\Projects menu item
        menuItem11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProjectChoice();
            }
        });

        menuBar.add(menu1);

        JMenu menu2 = new JMenu(messageHandler.getMessage("menu.titles.createscript"));
        JMenuItem menuItem20 = new JMenuItem(messageHandler.getMessage("menu.titles.createscript.new"));
        menu2.add(menuItem20);

        // This is the control for the Create GUI Script\Create New GUI Script menu item
        menuItem20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText(ComponentConstants.TEXTAREA_LABEL);
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu2);

        JMenu menu3 = new JMenu(messageHandler.getMessage("menu.titles.createcombo"));
        JMenuItem menuItem30 = new JMenuItem(messageHandler.getMessage("menu.titles.createcombo.new"));
        menu3.add(menuItem30);

        // This is the control for the Create Combo Options\Create New Combo Options menu item
        menuItem30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText(ComponentConstants.TEXTAREA_LABEL_COMBO);
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu3);

        JMenu menu4 = new JMenu(messageHandler.getMessage("menu.titles.help"));
        JMenuItem menuItem40 = new JMenuItem(messageHandler.getMessage("menu.titles.help.help"));
        menu4.add(menuItem40);
        menu4.addSeparator();
        JMenuItem menuItem42 = new JMenuItem(messageHandler.getMessage("menu.titles.help.about"));
        menu4.add(menuItem42);

        // This is the control for the Help\Help menu item
        menuItem40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Help in the Help menu has been clicked");
            }
        });

        JMenu menu5 = new JMenu(messageHandler.getMessage("menu.titles.compile"));
        menuItem50 = new JMenuItem(messageHandler.getMessage("menu.titles.compile.run"));
        menu5.add(menuItem50);

        // This is the control for the Compile & Run\Compile & Run and Test Gui menu item
        menuItem50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    if (StringUtils.isEmpty(comp0.getText())) {
                        if (StringUtils.isEmpty(comp0.getText())) {
                            JOptionPane.showMessageDialog(tg, WarningMessages.NO_FILES,
                                TITLE, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        try {
                            compileFile(projectName, testFile.getName());
                            runFileChoice();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, WarningMessages.NO_JAVA_PROJECT_SELECTED_COMPILE,
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        menuBar.add(menu5);

        JMenu menu6 = new JMenu(messageHandler.getMessage("menu.titles.copy"));
        JMenuItem menuItem60 = new JMenuItem(messageHandler.getMessage("menu.titles.copy.createjava"));
        menu6.add(menuItem60);
        JMenuItem menuItem62 = new JMenuItem(messageHandler.getMessage("menu.titles.copy.openexistingjava"));
        menu6.add(menuItem62);
        JMenuItem menuItem61 = new JMenuItem(messageHandler.getMessage("menu.titles.copy.copyjava"));
        menu6.add(menuItem61);
        menu6.addSeparator();
        JMenuItem menuItem63 = new JMenuItem(messageHandler.getMessage("menu.titles.copy.createspringboot"));
        menu6.add(menuItem63);
        JMenuItem menuItem64 = new JMenuItem(messageHandler.getMessage("menu.titles.copy.createwebspringboot"));
        menu6.add(menuItem64);

        // This is the control for the Create & Copy GUI\Create New GUI Project menu item
        menuItem60.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "code");
                GuiHelper.showFrame(npg);
            }
        });

        // This is the control for the Create & Copy GUI\Copy TestGUI to Project menu item
        menuItem61.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(javaProjectName) && testFile != null) {
                    try {
                        FileUtilities.fileCopy(Statics.RESOURCES_DIR + projectName + "\\TestGui.java", Statics.JAVA_PROJECTS_DIR + javaProjectName +
                                Statics.RELATIVE_PATH_FOR_GUIS + "TestGui.java");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(tg, InfoMessages.messageBuilder(javaProjectName, 0),
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (StringUtils.isEmpty(javaProjectName)) {
                        JOptionPane.showMessageDialog(tg, WarningMessages.NO_JAVA_PROJECT_SELECTED_COPY,
                                TITLE, JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tg, WarningMessages.NO_RECENT_SCRIPT,
                                TITLE, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // This is the control for the Create & Copy GUI\Open Existing GUI Project menu item
        menuItem62.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        // This is the control for the Create & Copy GUI\Create Basic SpringBoot Project menu item
        menuItem63.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "spring");
                GuiHelper.showFrame(npg);
            }
        });

        // This is the control for the Create & Copy GUI\Create Basic SpringBoot Web Project menu item
        menuItem64.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "spring-web");
                GuiHelper.showFrame(npg);
            }
        });

        menuBar.add(menu6);

        // This is the control for the Help\About menu item
        menuItem42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                About about = new About(tg);
                GuiHelper.showFrame(about);
            }
        });

        menuBar.add(menu4);

    }

    private int saveUnsavedInput() throws IOException {
        int res = 0;
        if (!StringUtils.isEmpty(comp0.getText())) {
            res = JOptionPane.showConfirmDialog(tg,
                    WarningMessages.UNSAVED_MATERIAL,
                    TITLE, JOptionPane.YES_NO_OPTION);
            if (res == 1) {
                comp0.clearTextArea();
            } else {
                openSaveFileChoice(Statics.GUI_EXTENSION);
            }
        }
        return res;
    }

    private void openSaveFileChoice(String extension) throws IOException {
        JFileChooser fc = new JFileChooser();
        if (!StringUtils.isEmpty(projectName)) {
            fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR + projectName));
        } else {
            fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR));
        }
        if (extension.equals(Statics.GUI_EXTENSION)) {
            openAndSaveToCorrectFileType(fc, extension, Statics.GUI_DESC);
        } else if (extension.equals(Statics.COMBO_OPTIONS_EXTENSION)) {
            openAndSaveToCorrectFileType(fc, extension, Statics.COMBO_DESC);
        }
    }

    private void openAndSaveToCorrectFileType(JFileChooser fc, String extension, String desc) throws IOException {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, extension.replace(".", ""));
        fc.setFileFilter(filter);
        int returnVal = fc.showSaveDialog(tg);
        if (returnVal == 0) {
            String filename = fc.getSelectedFile().getName().replace(Statics.COMBO_OPTIONS_EXTENSION, "");
            String allText = comp0.getText();
            FileUtilities.writeStringToFile(Statics.RESOURCES_DIR + projectName + "\\" + filename
                + Statics.COMBO_OPTIONS_EXTENSION, allText);
        }
    }

    private void openProjectChoice() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR));
        int returnVal = fc.showDialog(tg, ComponentConstants.FILECHOOSER_SEL_PROJ_APPROVE_BUTTON);

        if (returnVal == 0) {
            File file = fc.getSelectedFile();
            updateProjectSelection(file.getName());
        }
    }

    private int openOpenFileChoice(String desc, String extension) throws IOException {
        int numberOfFiles = 0;
        JFileChooser fc = new JFileChooser();
        //fc.getFileSelectionMode(JFileChooser.);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, extension.replace(".", ""));
        fc.setFileFilter(filter);
        if (!StringUtils.isEmpty(projectName)) {
            File file = new File(Statics.RESOURCES_DIR + projectName);
            fc.setCurrentDirectory(file);
            numberOfFiles = file.listFiles().length;
        } else {
            File file = new File(Statics.RESOURCES_DIR);
            fc.setCurrentDirectory(file);
            numberOfFiles = file.listFiles().length;
        }
        int returnVal = fc.showOpenDialog(tg);
        if (returnVal == 0) {
            File file = fc.getSelectedFile();
            testFile = file;
            updateBuiltFile(testFile.getName());
            FileUtilities.writeStringToFile(Statics.LAST_SCRIPT, "");
            String allText = FileUtilities.writeFileToString(Statics.RESOURCES_DIR + projectName + "\\" + file.getName());
            comp0.setText(allText);
            if (extension.equals(Statics.GUI_EXTENSION)) {
                menuItem50.setEnabled(true);
            } else if (extension.equals(Statics.COMBO_OPTIONS_EXTENSION)) {
                menuItem50.setEnabled(false);
            }
        } else {
            numberOfFiles = 0;
        }

        return numberOfFiles;
    }

    private void compileFile(String projName, String fileName) throws IOException {
        syncComponents();
        String allText = comp0.getText();
        FileUtilities.writeStringToFile(Statics.RESOURCES_DIR + projectName + "\\" + fileName, allText);
        //System.out.println("Creating GUI Properties and Compiling!");
        createGuiProperties(projName, fileName);
        //System.out.println("ScriptDirectedGui: Copying compiled GUI in to TestGui class!");
        String src = Statics.SCD_RESOURCES_DIR + "TestGui.java.tmp";
        String target = Statics.FINAL_GUI_TARGET_DIR + "TestGui.java";
        FileCopyUtils.copy(new File(src), new File(target));
        FileUtils.deleteQuietly(new File(src));
        if (!(new File(Statics.FINAL_GUI_DIR + projName + "\\" + fileName).exists())) {
            JOptionPane.showMessageDialog(tg, InfoMessages.messageBuilder(projName + "\\" +fileName, 1),
                TITLE, JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private void createGuiProperties(String projectName, String scriptName) {
        //System.out.println("ScriptDirectedGui: This method will run at startup and create the appropriate GUI properties from the input script");
        guiProperties.clearAllArrays();
        GuiScriptParser gsp = new GuiScriptParser(guiProperties);
        try {
            gsp.readInputScript(projectName, scriptName);
        } catch (GuiScriptFileException e) {
            e.printStackTrace();
        }
        GuiBuilder gb = new GuiBuilder(guiProperties);
        gb.buildGuiClass();
    }

    private void runFileChoice() {
        runBuildScript("runScript");
    }

    public void updateProjectSelection(String newValue) {
        projectName = newValue;
        comp1.setLabelText(Statics.PROJECT_SEL_MESSAGE + newValue);
        p1.repaint();
    }

    public void updateJavaProjectSelection(String value) {
        javaProjectName = value;
        comp3.setLabelText(Statics.PROJECT_SEL_MESSAGE + value);
        p1.repaint();
    }

    public void updateBuiltFile(String newValue) {
        comp2.setLabelText(Statics.LAST_FILE_BUILT_MESSAGE + newValue);
        p1.repaint();
    }

    private void syncComponents() throws IOException {

        //This method updates the template project with the latest versions of the Gui Components

        FileUtilities.copyAllFilesFromSrcDirToTargetDir(Statics.JAVA_PROJECTS_DIR + Statics.NEW_GUI_CREATOR_PROJECT + Statics.RELATIVE_PATH_FOR_COMPONENTS,
            Statics.JAVA_PROJECTS_DIR + Statics.TEMPLATE_PROJECT + Statics.RELATIVE_PATH_FOR_COMPONENTS);
        FileUtilities.copyAllFilesFromSrcDirToTargetDir(Statics.JAVA_PROJECTS_DIR + Statics.NEW_GUI_CREATOR_PROJECT + Statics.RELATIVE_PATH_FOR_COMPONENTS,
            Statics.JAVA_PROJECTS_DIR + Statics.TEST_GUI_RUNNER_PROJECT + Statics.RELATIVE_PATH_FOR_COMPONENTS);
    }

    private Thread runBuildScript(String fileToRun) {
        ScriptRunner sr = new ScriptRunner(fileToRun);

        Thread thread = new Thread(sr);
        thread.start();

        return thread;
    }
}
