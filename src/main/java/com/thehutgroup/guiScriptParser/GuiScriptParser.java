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
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class GuiScriptParser {

    private GuiProperties guiProperties;

    private int yposition = 90;
    private static final String RESOURCES_DIR = "C:\\GradleTutorials\\ScriptDirectedGui\\GuiSourceFiles\\";
    private String projectName;

    @Autowired
    public GuiScriptParser(GuiProperties guiProperties) {
        this.guiProperties = guiProperties;
    }

    public void readInputScript(String projectName, String scriptName) {

        this.projectName = projectName;

        boolean componentsFlag = false;
        boolean buttonsFlag = false;
        boolean menuesFlag = false;
        ArrayList<String> components = new ArrayList<String>();
        ArrayList<String> buttons = new ArrayList<String>();
        ArrayList<String> menues = new ArrayList<String>();

        // The name of the file to open.
        //Path currentRelativePath = Paths.get("");
        //String currentDir = currentRelativePath.toAbsolutePath().toString();
        String fileName = RESOURCES_DIR + projectName + "\\" + scriptName;
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("ScriptDirectedGui: Cannot find the script - " + scriptName);
            System.out.println("ScriptDirectedGui: Please ensure script is saved in directory - " + RESOURCES_DIR);
            System.out.println("ScriptDirectedGui: Closing down");
            System.exit(0);
        }

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if (!StringUtils.isEmpty(line)) {
                    if (!componentsFlag && !buttonsFlag && !menuesFlag) {
                        if (!line.contains("components:") && !line.contains("buttons:") && !line.contains("menus")) {
                            parseLine(line);
                        } else {
                            if (line.contains("menus:")) {
                                menuesFlag = true;
                            }
                        }
                    } else {
                        if (menuesFlag) {
                            if (line.contains("components:")) {
                                componentsFlag = true;
                                menuesFlag = false;
                                parseMenues(menues);
                            } else {
                                menues.add(line);
                            }
                        } else if (componentsFlag) {
                            if (line.contains("buttons:")) {
                                buttonsFlag = true;
                                componentsFlag = false;
                                parseComponents(components);
                            } else {
                                components.add(line);
                            }
                        } else if (buttonsFlag) {
                            buttons.add(line);
                        }
                    }
                }
            }
            parseButtons(buttons);

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public void parseMenues(ArrayList<String> menues) {
        for (int i = 0; i < menues.size(); i++) {
            guiProperties.getMenues().add(menues.get(i));
        }
    }

    public void parseButtons(ArrayList<String> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            String input = buttons.get(i).replace("    [", "").replace("],", ""). replace("]", "");
            FreeButton obj = new FreeButton(input, 0, 0);
            guiProperties.getButtons().add(obj);
        }
    }

    public void parseComponents(ArrayList<String> components) {
        for (int i = 0; i < components.size(); i++) {
            String input = components.get(i).replace("[", "").replace("],", ""). replace("]", "");
            if (input.contains("FreeCheckBox")) {
                input = input.replace("    FreeCheckBox: ", "");
                String[] inputs = input.split(", ");
                FreeCheckBox obj = new FreeCheckBox(null, inputs[0], 30, yposition, Integer.parseInt(inputs[1]), 20);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(false);
            } else if (input.contains("FreeComboBox")) {
                input = input.replace("    FreeComboBox: ", "");
                String[] inputs = input.split(", ");
                ArrayList<String> comboOptions = getComboOptionsFromFile(inputs[1]);
                FreeComboBox obj = new FreeComboBox(30, yposition, Integer.parseInt(inputs[0]), 20, comboOptions);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(false);
            } else if (input.contains("FreeLabelTextButtonTriple")) {
                input = input.replace("    FreeLabelTextButtonTriple: ", "");
                String[] inputs = input.split(", ");
                FreeLabelTextButtonTriple obj = new FreeLabelTextButtonTriple(null, inputs[0], 30, yposition, Integer.parseInt(inputs[1]), inputs[2]);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(true);
            } else if (input.contains("FreeLabelComboBoxPair")) {
                input = input.replace("    FreeLabelComboBoxPair: ", "");
                String[] inputs = input.split(", ");
                ArrayList<String> comboOptions = getComboOptionsFromFile(inputs[2]);
                FreeLabelComboBoxPair obj = new FreeLabelComboBoxPair(null, inputs[0], 30, yposition, Integer.parseInt(inputs[1]), comboOptions);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(true);
            } else if (input.contains("FreeLabelTextFieldPair")) {
                input = input.replace("    FreeLabelTextFieldPair: ", "");
                String[] inputs = input.split(", ");
                FreeLabelTextFieldPair obj = new FreeLabelTextFieldPair(null, inputs[0], 30, yposition, Integer.parseInt(inputs[1]));
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(true);
            } else if (input.contains("FreeRadioButtonGroup")) {
                input = input.replace("    FreeRadioButtonGroup: ", "");
                String[] inputs = input.split(", ");
                FreeRadioButtonGroup obj = new FreeRadioButtonGroup();
                for (int j = 0; j < inputs.length; j++) {
                    if (j % 2 == 0) {
                        FreeRadioButton rb = new FreeRadioButton(null, inputs[j], 30, yposition,Integer.parseInt(inputs[j + 1]), 20);
                        guiProperties.getRadioButtons().add(rb);
                    }
                }
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(false);
            } else if (input.contains("FreeTextArea")) {
                input = input.replace("    FreeTextArea: ", "");
                String[] inputs = input.split(", ");
                FreeTextArea obj = new FreeTextArea(null, inputs[0], 30, yposition, Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]), Boolean.parseBoolean(inputs[4]));
                yposition = yposition + 50 + Integer.parseInt(inputs[3]);
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(true);
            } else if (input.contains("FreeTextField")) {
                input = input.replace("    FreeTextField: ", "");
                String[] inputs = input.split(", ");
                FreeTextField obj = new FreeTextField(inputs[0],30, yposition, Integer.parseInt(inputs[1]), 20);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(false);
            } else if (input.contains("FreeLabel")) {
                input = input.replace("    FreeLabel: ", "");
                String[] inputs = input.split(", ");
                FreeLabel obj = new FreeLabel(inputs[0], 30, yposition, Integer.parseInt(inputs[1]), 20);
                yposition = yposition + 50;
                guiProperties.getComponents().add(obj);
                guiProperties.getPanelComponent().add(false);
            }
        }
    }

    public ArrayList<String> getComboOptionsFromFile(String input) {

        ArrayList<String> all = new ArrayList<String>();

        // The name of the file to open.
        //Path currentRelativePath = Paths.get("");
        //String currentDir = currentRelativePath.toAbsolutePath().toString();
        String fileName = RESOURCES_DIR + projectName + "\\" + input;

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                all.add(line);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return all;
    }

    public void parseLine(String line) {
        if (line.contains("dimension:")) {
            String input = line.replace("dimension: ", "");
            String[] inputs = input.split(", ");
            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i].contains("xsize=")) {
                    String temp = inputs[i].replace("xsize=", "");
                    guiProperties.setFrameXSize(Integer.parseInt(temp));
                } else if (inputs[i].contains("ysize=")) {
                    String temp = inputs[i].replace("ysize=", "");
                    guiProperties.setFrameYSize(Integer.parseInt(temp));
                }
            }
        } else if (line.contains("backgroundcolor:")) {
            String input = line.replace("backgroundcolor: value=", "");
            String[] inputs = input.split(", ");
            Color temp = new Color(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
            guiProperties.setGuiBackgroundColor(temp);
        } else if (line.contains("heading:") && !line.contains("subheading")) {
            String input = line.replace("heading: value=", "");
            guiProperties.setMainHeading(input);
        } else if (line.contains("subheading:")) {
            String input = line.replace("subheading: value=", "");
            guiProperties.setSubHeading(input);
        }
    }

}
