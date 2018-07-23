package com.thehutgroup.createdgui;

import com.thehutgroup.guiScriptParser.GuiBuilder;
import com.thehutgroup.guiScriptParser.GuiScriptParser;
import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeTextArea;
import com.thehutgroup.guicomponents.GuiProperties;
import com.thehutgroup.guis.GuiHelper;
import com.thehutgroup.runners.ScriptRunner;
import com.thehutgroup.statics.MenuTitles;
import com.thehutgroup.statics.Statics;
import com.thehutgroup.testingarea.TestGui;
import com.thehutgroup.utilities.FileUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    JMenuItem menuItem51;

    private TestGui[] allTestGuis = new TestGui[200];
    private int testGuiCount = 0;

    private MainGui tg = this;

    private JMenuBar menuBar = new JMenuBar();

    private JPanel p1 = new JPanel();

    private FreeTextArea comp0 = new FreeTextArea(col, "Please enter your new script below:", 30, 90, 300, 935, 620, false);

    private static final String PROJECT_SEL_MESSAGE = "Project selected: ";
    private static final String JAVA_PROJ_SEL_MESSAGE = "Java Project selected: ";
    private static final String NO_PROJ_MESSAGE = "There is currently no project selected";
    private static final String LAST_FILE_BUILT_MESSAGE = "The selected script file is: ";

    private FreeLabel comp1 = new FreeLabel(PROJECT_SEL_MESSAGE + NO_PROJ_MESSAGE, 30, 750, 400, 20);
    private FreeLabel comp2 = new FreeLabel(LAST_FILE_BUILT_MESSAGE, 30, 790, 400, 20);
    private FreeLabel comp3 = new FreeLabel(JAVA_PROJ_SEL_MESSAGE + NO_PROJ_MESSAGE, 30, 770, 400, 20);

    private String projectName = "";
    private String javaProjectName = "";
    private String comboOptionsFile = "";
    private File testFile;

    private String currentText = "";

    private GuiProperties guiProperties;

    @Autowired
    public MainGui(GuiProperties guiProperties) {

        this.guiProperties = guiProperties;

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

        comp0.getTextArea().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!comp0.getText().equals(currentText)) {
                    menuItem51.setEnabled(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!comp0.getText().equals(currentText)) {
                    menuItem51.setEnabled(false);
                }            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!comp0.getText().equals(currentText)) {
                    menuItem51.setEnabled(false);
                }            }
        });
    }

    private void setUpMenuBar() {
        JMenu menu0 = new JMenu(MenuTitles.FILE);
        JMenuItem menuItem00 = new JMenuItem(MenuTitles.OPEN_GUI);
        menu0.add(menuItem00);
        JMenuItem menuItem01 = new JMenuItem(MenuTitles.SAVE_GUI);
        menu0.add(menuItem01);
        JMenuItem menuItem02 = new JMenuItem(MenuTitles.CLOSE_GUI);
        menu0.add(menuItem02);
        menu0.addSeparator();
        JMenuItem menuItem05 = new JMenuItem(MenuTitles.OPEN_OPTIONS);
        menu0.add(menuItem05);
        JMenuItem menuItem06 = new JMenuItem(MenuTitles.SAVE_OPTIONS);
        menu0.add(menuItem06);
        JMenuItem menuItem07 = new JMenuItem(MenuTitles.CLOSE_OPTIONS);
        menu0.add(menuItem07);
        menu0.addSeparator();
        JMenuItem menuItem04 = new JMenuItem(MenuTitles.EXIT);
        menu0.add(menuItem04);

        // This is the control for the File\Open File menu item
        menuItem00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    try {
                        if (openOpenFileChoice("GUI Files", "gui") == 0) {
                            JOptionPane.showMessageDialog(tg, "No file selected, or no files in project",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, "No project currently selected - select/create a project before opening a file",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // This is the control for the File\Save File menu item
        menuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comp1.getLabelText().equals(NO_PROJ_MESSAGE)) {

                } else {
                    try {
                        openSaveFileChoice();
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
                resetCompileOptions();
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
                        if (openOpenFileChoice("Combo Files", "combo") == 0) {
                            JOptionPane.showMessageDialog(tg, "No file selected, or no combo options files in project",
                                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, "No project currently selected - select/create a project before opening a file",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        menuItem07.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.clearTextArea();
            }
        });

        menuBar.add(menu0);

        JMenu menu1 = new JMenu(MenuTitles.PROJECTS);
        JMenuItem menuItem10 = new JMenuItem(MenuTitles.CREATE_PROJ);
        menu1.add(menuItem10);
        JMenuItem menuItem11 = new JMenuItem(MenuTitles.OPEN_PROJ);
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

        JMenu menu2 = new JMenu(MenuTitles.CREATE_SCRIPT);
        JMenuItem menuItem20 = new JMenuItem(MenuTitles.CREATE_NEW_SCRIPT);
        menu2.add(menuItem20);

        // This is the control for the Create GUI Script\Create New GUI Script menu item
        menuItem20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText("Please enter your new script below:");
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu2);

        JMenu menu3 = new JMenu(MenuTitles.CREATE_OPTIONS);
        JMenuItem menuItem30 = new JMenuItem(MenuTitles.CREATE_NEW_OPTIONS);
        menu3.add(menuItem30);

        // This is the control for the Create Combo Options\Create New Combo Options menu item
        menuItem30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText("Please list your new combo options below:");
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu3);

        JMenu menu4 = new JMenu(MenuTitles.HELP);
        JMenuItem menuItem40 = new JMenuItem(MenuTitles.HELP_HELP);
        menu4.add(menuItem40);
        menu4.addSeparator();
        JMenuItem menuItem42 = new JMenuItem(MenuTitles.HELP_ABOUT);
        menu4.add(menuItem42);

        // This is the control for the Help\Help menu item
        menuItem40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Help in the Help menu has been clicked");
            }
        });

        JMenu menu5 = new JMenu(MenuTitles.COMPILE_TEST);
        JMenuItem menuItem50 = new JMenuItem(MenuTitles.COMPILE_TEST_GUI);
        menu5.add(menuItem50);
        menuItem51 = new JMenuItem(MenuTitles.RUN_TEST_GUI);
        menu5.add(menuItem51);

        // This is the control for the Compile\Compile and Test Gui menu item
        menuItem50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    if (StringUtils.isEmpty(comp0.getText()) || comp0.getText().equals(currentText)) {
                        if (StringUtils.isEmpty(comp0.getText())) {
                            JOptionPane.showMessageDialog(tg, "No file selected, or no files in project",
                                TITLE, JOptionPane.INFORMATION_MESSAGE);
                        } else if (comp0.getText().equals(currentText)) {
                            JOptionPane.showMessageDialog(tg, "This code has already been compiled",
                                TITLE, JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        try {
                            compileFile(projectName, testFile.getName());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, "No project currently selected - select/create a project before compiling",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        menuItem51.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    runFileChoice();
                } else {
                    JOptionPane.showMessageDialog(tg, "No project currently selected - select/create a project before compiling",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        menuItem51.setEnabled(false);

        menuBar.add(menu5);

        JMenu menu6 = new JMenu(MenuTitles.CREATE_COPY);
        JMenuItem menuItem60 = new JMenuItem(MenuTitles.CREATE_GUI_JAVA);
        menu6.add(menuItem60);
        JMenuItem menuItem62 = new JMenuItem(MenuTitles.OPEN_EXISTING_JAVA);
        menu6.add(menuItem62);
        JMenuItem menuItem61 = new JMenuItem(MenuTitles.COPY_GUI_JAVA);
        menu6.add(menuItem61);
        menu6.addSeparator();
        JMenuItem menuItem63 = new JMenuItem(MenuTitles.CREATE_SPRINGBOOT);
        menu6.add(menuItem63);
        JMenuItem menuItem64 = new JMenuItem(MenuTitles.CREATE_SPRINGBOOT_WEB);
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
                    JOptionPane.showMessageDialog(tg, "TestGui.java has been successfully copied to the " + javaProjectName + " project",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (StringUtils.isEmpty(javaProjectName)) {
                        JOptionPane.showMessageDialog(tg, "No Java project currently selected - select/create a Java project before copying",
                                TITLE, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tg, "No recently built script - build a script before copying",
                                TITLE, JOptionPane.INFORMATION_MESSAGE);
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

    private void resetCompileOptions() {
        menuItem51.setText(MenuTitles.RUN_TEST_GUI);
        menuItem51.setEnabled(false);
    }

    private int saveUnsavedInput() throws IOException {
        int res = 0;
        if (!StringUtils.isEmpty(comp0.getText())) {
            res = JOptionPane.showConfirmDialog(tg,
                    "There is potentially unsaved material in your text area - do you wish to save this before continuing?",
                    TITLE, JOptionPane.YES_NO_OPTION);
            if (res == 1) {
                comp0.clearTextArea();
            } else {
                openSaveFileChoice();
            }
        }
        return res;
    }

    private void openSaveFileChoice() throws IOException {
        JFileChooser fc = new JFileChooser();
        if (!StringUtils.isEmpty(projectName)) {
            fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR + projectName));
        } else {
            fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR));
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GUI Files","gui");
        fc.setFileFilter(filter);
        int returnVal = fc.showSaveDialog(tg);
        if (returnVal == 0) {
            String filename = fc.getSelectedFile().getName().replace(Statics.GUI_EXTENSION, "");
            String allText = comp0.getText();
            FileUtilities.writeStringToFile(Statics.RESOURCES_DIR + projectName + "\\" + filename
                    + Statics.GUI_EXTENSION, allText);
        }
    }

    private void openProjectChoice() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(Statics.RESOURCES_DIR));
        int returnVal = fc.showDialog(tg, "Select Project");

        if (returnVal == 0) {
            File file = fc.getSelectedFile();
            updateProjectSelection(file.getName());
        }
    }

    private int openOpenFileChoice(String desc, String extension) throws IOException {
        int numberOfFiles = 0;
        JFileChooser fc = new JFileChooser();
        //fc.getFileSelectionMode(JFileChooser.);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc,extension);
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
            menuItem51.setText("Run TestGui - " + testFile.getName());
            menuItem51.setEnabled(false);
            FileUtilities.writeStringToFile(Statics.LAST_SCRIPT, "");
            String allText = FileUtilities.writeFileToString(Statics.RESOURCES_DIR + projectName + "\\" + file.getName());
            comp0.setText(allText);
        } else {
            numberOfFiles = 0;
        }

        return numberOfFiles;
    }

    private void compileFile(String projName, String fileName) throws IOException {
        String allText = comp0.getText();
        currentText = allText;
        FileUtilities.writeStringToFile(Statics.RESOURCES_DIR + projectName + "\\" + fileName, allText);
        System.out.println("Creating GUI Properties and Compiling!");
        createGuiProperties(projName, fileName);
        System.out.println("ScriptDirectedGui: Copying compiled GUI in to TestGui class!");
        String src = Statics.SCD_RESOURCES_DIR + "TestGui.java.tmp";
        String target = Statics.FINAL_GUI_TARGET_DIR + "TestGui.java";
        FileCopyUtils.copy(new File(src), new File(target));
        FileUtils.deleteQuietly(new File(src));
        if (!(new File(Statics.FINAL_GUI_DIR + projName + "\\" + fileName).exists())) {
            System.out.println("ScriptDirectedGui: ERROR - The file, " + projName + "\\" +fileName + " does not exist in the GUI script source directory - exiting!");
        }
        JOptionPane.showMessageDialog(tg, "The code has compiled successfully - ready to test!",
                TITLE, JOptionPane.INFORMATION_MESSAGE);
        menuItem51.setEnabled(true);
    }

    private void createGuiProperties(String projectName, String scriptName) {
        System.out.println("ScriptDirectedGui: This method will run at startup and create the appropriate GUI properties from the input script");
        guiProperties.clearAllArrays();
        GuiScriptParser gsp = new GuiScriptParser(guiProperties);
        gsp.readInputScript(projectName, scriptName);
        GuiBuilder gb = new GuiBuilder(guiProperties);
        gb.buildGuiClass();
    }

    private void runFileChoice() {
        runBuildScript("runScript");
    }

    public void updateProjectSelection(String newValue) {
        projectName = newValue;
        comp1.setLabelText(PROJECT_SEL_MESSAGE + newValue);
        p1.repaint();
    }

    public void updateJavaProjectSelection(String value) {
        javaProjectName = value;
        comp3.setLabelText(PROJECT_SEL_MESSAGE + value);
        p1.repaint();
    }

    public void updateBuiltFile(String newValue) {
        comp2.setLabelText(LAST_FILE_BUILT_MESSAGE + newValue);
        p1.repaint();
    }

    private Thread runBuildScript(String fileToRun) {
        ScriptRunner sr = new ScriptRunner(fileToRun);

        Thread thread = new Thread(sr);
        thread.start();

        return thread;
    }
}
