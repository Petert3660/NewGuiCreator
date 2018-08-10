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

    private static String TITLE;
    private static String GUI_EXT;
    private static String COMBO_EXT;

    public static final int EXIT_STATUS = 0;
    private static final int FRAME_X_SIZE = 1000;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(235, 255, 255);

    private MainGui tg = this;

    private JMenuBar menuBar = new JMenuBar();

    private JPanel p1 = new JPanel();

    private FreeTextArea comp0 = new FreeTextArea(col, null, 30, 90, 300, 935, 620, false);

    private FreeLabel comp1;
    private FreeLabel comp2;
    private FreeLabel comp3;

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

        TITLE = messageHandler.getMessage("constants.mainheading");
        GUI_EXT = messageHandler.getMessage("filepaths.gui.ext");
        COMBO_EXT = messageHandler.getMessage("filepaths.combo.ext");

        comp0.setLabelText(messageHandler.getMessage("components.textarea.label"));

        comp1 = new FreeLabel(messageHandler.getMessage("constants.project.selectedmessage",
            new String[]{messageHandler.getMessage("constants.project.noprojectmessage")}),30, 750, 400, 20);

        comp2 = new FreeLabel(messageHandler.getMessage("constants.project.lastbuiltmessage", new String[]{""}), 30, 790, 400, 20);

        comp3 = new FreeLabel(messageHandler.getMessage("constants.project.javaselectedmessage",
            new String[]{messageHandler.getMessage("constants.project.noprojectmessage")}), 30, 770, 400, 20);

        this.setTitle(messageHandler.getMessage("constants.mainheading"));
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(messageHandler.getMessage("constants.mainheading"), 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.EXIT, 460, 800, 80);

        // This is the control for the Exit-implement button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(EXIT_STATUS);
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
        JMenu menu0 = new JMenu(messageHandler.getMessage("components.menu.titles.file"));
        JMenuItem menuItem00 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.open.guiscript"));
        menu0.add(menuItem00);
        JMenuItem menuItem01 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.save.guiscript"));
        menu0.add(menuItem01);
        JMenuItem menuItem02 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.close.guiscript"));
        menu0.add(menuItem02);
        menu0.addSeparator();
        JMenuItem menuItem05 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.open.combooption"));
        menu0.add(menuItem05);
        JMenuItem menuItem06 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.save.combooption"));
        menu0.add(menuItem06);
        JMenuItem menuItem07 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.close.combooption"));
        menu0.add(menuItem07);
        menu0.addSeparator();
        JMenuItem menuItem04 = new JMenuItem(messageHandler.getMessage("components.menu.titles.file.exit"));
        menu0.add(menuItem04);

        // This is the control for the File\Open File menu item
        menuItem00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    try {
                        if (openOpenFileChoice("GUI Files", GUI_EXT) == 0) {
                            JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.noguifiles"),
                                    TITLE, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.noprojectselected"),
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // This is the control for the File\Save File menu item
        menuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFileTypeToOpen(GUI_EXT);
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
                System.exit(EXIT_STATUS);
            }
        });

        menuItem05.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    try {
                        if (openOpenFileChoice(messageHandler.getMessage("filepaths.combo.description"), COMBO_EXT) == 0) {
                            JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.nocombofiles"),
                                    TITLE, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.noprojectselected"),
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        menuItem06.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFileTypeToOpen(COMBO_EXT);
            }
        });

        menuItem07.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.clearTextArea();
            }
        });

        menuBar.add(menu0);

        JMenu menu1 = new JMenu(messageHandler.getMessage("components.menu.titles.project"));
        JMenuItem menuItem10 = new JMenuItem(messageHandler.getMessage("components.menu.titles.project.create"));
        menu1.add(menuItem10);
        JMenuItem menuItem11 = new JMenuItem(messageHandler.getMessage("components.menu.titles.project.open"));
        menu1.add(menuItem11);

        // This is the control for the Create New Project\Projects menu item
        menuItem10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "script", messageHandler);
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

        JMenu menu2 = new JMenu(messageHandler.getMessage("components.menu.titles.createscript"));
        JMenuItem menuItem20 = new JMenuItem(messageHandler.getMessage("components.menu.titles.createscript.new"));
        menu2.add(menuItem20);

        // This is the control for the Create GUI Script\Create New GUI Script menu item
        menuItem20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText(messageHandler.getMessage("components.textarea.label"));
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu2);

        JMenu menu3 = new JMenu(messageHandler.getMessage("components.menu.titles.createcombo"));
        JMenuItem menuItem30 = new JMenuItem(messageHandler.getMessage("components.menu.titles.createcombo.new"));
        menu3.add(menuItem30);

        // This is the control for the Create Combo Options\Create New Combo Options menu item
        menuItem30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setLabelText(messageHandler.getMessage("components.textarea.label.combo"));
                p1.repaint();
                try {
                    int res = saveUnsavedInput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        menuBar.add(menu3);

        JMenu menu4 = new JMenu(messageHandler.getMessage("components.menu.titles.help"));
        JMenuItem menuItem40 = new JMenuItem(messageHandler.getMessage("components.menu.titles.help.help"));
        menu4.add(menuItem40);
        menu4.addSeparator();
        JMenuItem menuItem42 = new JMenuItem(messageHandler.getMessage("components.menu.titles.help.about"));
        menu4.add(menuItem42);

        // This is the control for the Help\Help menu item
        menuItem40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Help in the Help menu has been clicked");
            }
        });

        JMenu menu5 = new JMenu(messageHandler.getMessage("components.menu.titles.compile"));
        menuItem50 = new JMenuItem(messageHandler.getMessage("components.menu.titles.compile.run"));
        menu5.add(menuItem50);

        // This is the control for the Compile & Run\Compile & Run and Test Gui menu item
        menuItem50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(projectName)) {
                    if (StringUtils.isEmpty(comp0.getText())) {
                        if (StringUtils.isEmpty(comp0.getText())) {
                            JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.nofiles"),
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
                    JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.nojavaprojectselectedcompile"),
                            TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        menuBar.add(menu5);

        JMenu menu6 = new JMenu(messageHandler.getMessage("components.menu.titles.copy"));
        JMenuItem menuItem60 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.createjava"));
        menu6.add(menuItem60);
        JMenuItem menuItem62 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.openexistingjava"));
        menu6.add(menuItem62);
        JMenuItem menuItem61 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.copyjava"));
        menu6.add(menuItem61);
        menu6.addSeparator();
        JMenuItem menuItem63 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.createspringboot"));
        menu6.add(menuItem63);
        JMenuItem menuItem65 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.createguispringboot"));
        menu6.add(menuItem65);
        JMenuItem menuItem64 = new JMenuItem(messageHandler.getMessage("components.menu.titles.copy.createwebspringboot"));
        menu6.add(menuItem64);

        // This is the control for the Create & Copy GUI\Create New GUI Project menu item
        menuItem60.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "code", messageHandler);
                GuiHelper.showFrame(npg);
            }
        });

        // This is the control for the Create & Copy GUI\Copy TestGUI to Project menu item
        menuItem61.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(javaProjectName) && testFile != null) {
                    try {
                        FileUtilities.fileCopy(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName + "/TestGui.java"}),
                            messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName + "/TestGui.java"}));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.info.javacopysuccess", new String[]{javaProjectName}),
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (StringUtils.isEmpty(javaProjectName)) {
                        JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.nojavaprojectselectedcopy"),
                                TITLE, JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.norecentscript"),
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
                NewProjectGui npg = new NewProjectGui(tg, "spring", messageHandler);
                GuiHelper.showFrame(npg);
            }
        });

        // This is the control for the Create & Copy GUI\Create Basic SpringBoot Web Project menu item
        menuItem64.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectGui npg = new NewProjectGui(tg, "spring-web", messageHandler);
                GuiHelper.showFrame(npg);
            }
        });

        menuBar.add(menu6);

        // This is the control for the Help\About menu item
        menuItem42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                About about = new About(tg, messageHandler);
                GuiHelper.showFrame(about);
            }
        });

        menuBar.add(menu4);

    }

    private void selectFileTypeToOpen(String extension) {
        if (StringUtils.isEmpty(projectName)) {
            JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.warning.noprojectselected"),
                TITLE, JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                openSaveFileChoice(extension);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private int saveUnsavedInput() throws IOException {
        int res = 0;
        if (!StringUtils.isEmpty(comp0.getText())) {
            res = JOptionPane.showConfirmDialog(tg,
                messageHandler.getMessage("messages.warning.unsavedmaterial"),
                    TITLE, JOptionPane.YES_NO_OPTION);
            if (res == 1) {
                comp0.clearTextArea();
            } else {
                openSaveFileChoice(GUI_EXT);
            }
        }
        return res;
    }

    private void openSaveFileChoice(String extension) throws IOException {
        JFileChooser fc = new JFileChooser();
        if (!StringUtils.isEmpty(projectName)) {
            fc.setCurrentDirectory(new File(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName})));
        } else {
            fc.setCurrentDirectory(new File(messageHandler.getMessage("filepaths.resourcedir", new String[]{""})));
        }
        if (extension.equals(GUI_EXT)) {
            openAndSaveToCorrectFileType(fc, extension, messageHandler.getMessage("filepaths.gui.description"));
        } else if (extension.equals(COMBO_EXT)) {
            openAndSaveToCorrectFileType(fc, extension, messageHandler.getMessage("filepaths.combo.description"));
        }
    }

    private void openAndSaveToCorrectFileType(JFileChooser fc, String extension, String desc) throws IOException {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, extension.replace(".", ""));
        fc.setFileFilter(filter);
        int returnVal = fc.showSaveDialog(tg);
        if (returnVal == 0) {
            String filename = fc.getSelectedFile().getName().replace(COMBO_EXT, "");
            String allText = comp0.getText();
            FileUtilities.writeStringToFile(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName + "/" + filename})
                + extension, allText);
        }
    }

    private void openProjectChoice() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(messageHandler.getMessage("filepaths.resourcedir", new String[]{""})));
        int returnVal = fc.showDialog(tg,messageHandler.getMessage("components.filechooser.approve.button.selectproject"));

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
            File file = new File(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName}));
            fc.setCurrentDirectory(file);
            numberOfFiles = file.listFiles().length;
        } else {
            File file = new File(messageHandler.getMessage("filepaths.resourcedir", new String[]{""}));
            fc.setCurrentDirectory(file);
            numberOfFiles = file.listFiles().length;
        }
        int returnVal = fc.showOpenDialog(tg);
        if (returnVal == 0) {
            File file = fc.getSelectedFile();
            testFile = file;
            updateBuiltFile(testFile.getName());
            FileUtilities.writeStringToFile(messageHandler.getMessage("filepaths.lastscript"), "");
            String allText = FileUtilities.writeFileToString(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName + "/" + file.getName()}));
            comp0.setText(allText);
            if (extension.equals(GUI_EXT)) {
                menuItem50.setEnabled(true);
            } else if (extension.equals(COMBO_EXT)) {
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
        FileUtilities.writeStringToFile(messageHandler.getMessage("filepaths.resourcedir", new String[]{projectName + "/" + fileName}) , allText);
        //System.out.println("Creating GUI Properties and Compiling!");
        createGuiProperties(projName, fileName);
        //System.out.println("ScriptDirectedGui: Copying compiled GUI in to TestGui class!");
        String src = messageHandler.getMessage("filepaths.scd.resourcedir", new String[]{"TestGui.java.tmp"});
        String target = messageHandler.getMessage("filepaths.finalguitargetdir", new String[]{"TestGui.java"});
        FileCopyUtils.copy(new File(src), new File(target));
        FileUtils.deleteQuietly(new File(src));
        if (!(new File(messageHandler.getMessage("filepaths.finalguidir", new String[]{projName + "/" + fileName})).exists())) {
            JOptionPane.showMessageDialog(tg, messageHandler.getMessage("messages.error.missingscriptfile", new String[]{projName + "/" + fileName}),
                TITLE, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void createGuiProperties(String projectName, String scriptName) {
        //System.out.println("ScriptDirectedGui: This method will run at startup and create the appropriate GUI properties from the input script");
        guiProperties.clearAllArrays();
        GuiScriptParser gsp = new GuiScriptParser(guiProperties, messageHandler);
        try {
            gsp.readInputScript(projectName, scriptName);
        } catch (GuiScriptFileException e) {
            e.printStackTrace();
        }
        GuiBuilder gb = new GuiBuilder(guiProperties, messageHandler);
        gb.buildGuiClass();
    }

    private void runFileChoice() {
        runBuildScript("runScript");
    }

    public void updateProjectSelection(String newValue) {
        projectName = newValue;
        comp1.setLabelText( messageHandler.getMessage("constants.project.selectedmessage", new String[]{newValue}));
        p1.repaint();
    }

    public void updateJavaProjectSelection(String value) {
        javaProjectName = value;
        comp3.setLabelText( messageHandler.getMessage("constants.project.selectedmessage", new String[]{value}));
        p1.repaint();
    }

    public void updateBuiltFile(String newValue) {
        comp2.setLabelText(messageHandler.getMessage("constants.project.lastbuiltmessage", new String[]{newValue}));
        p1.repaint();
    }

    private void syncComponents() throws IOException {

        //This method updates the template project with the latest versions of the Gui Components

        String rpc = messageHandler.getMessage("filepaths.relativepathforcomponents", new String[]{""});
        String rpcpt = messageHandler.getMessage("filepaths.relativepathforcomponents.ptconsultancy", new String[]{""});
        String srcDir = messageHandler.getMessage("filepaths.newguicreatorproject", new String[]{rpc});
        String targDir = messageHandler.getMessage("filepaths.templateproject", new String[]{rpcpt});
        String targDirRun = messageHandler.getMessage("filepaths.testrunner", new String[]{rpc});

        FileUtilities.copyAllFilesFromSrcDirToTargetDir(messageHandler.getMessage("filepaths.javaprojectsdir", new String[]{srcDir}),
            messageHandler.getMessage("filepaths.javaprojectsdir", new String[]{targDir}));
        FileUtilities.copyAllFilesFromSrcDirToTargetDir(messageHandler.getMessage("filepaths.javaprojectsdir", new String[]{srcDir}),
            messageHandler.getMessage("filepaths.javaprojectsdir", new String[]{targDirRun}));
    }

    private Thread runBuildScript(String fileToRun) {
        ScriptRunner sr = new ScriptRunner(fileToRun);

        Thread thread = new Thread(sr);
        thread.start();

        return thread;
    }
}
