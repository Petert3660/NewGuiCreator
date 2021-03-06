package com.thehutgroup.guiScriptParser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.thehutgroup.exceptions.ComboBoxItemsFileException;
import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeCheckBox;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeLabelTextButtonTriple;
import com.thehutgroup.guicomponents.FreeLabelTextFieldPair;
import com.thehutgroup.guicomponents.FreeTextArea;
import com.thehutgroup.guicomponents.FreeTextField;
import com.thehutgroup.guis.GuiProperties;
import com.thehutgroup.messages.MessageHandler;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestGuiScriptParser {

    private static final int ARRAY_SIZE_ZERO = 0;
    private static final int ARRAY_SIZE_ONE = 1;
    private static final String TEST_MENU = "    [File: Open, Close, -separator, Exit-implement]";
    private static final String TEST_BUTTON_INPUT = "    [Exit-implement]";
    private static final String TEST_BUTTON_OUTPUT = "Exit-implement";
    private static final String TEST_LABEL_INPUT = "    FreeLabel: NewGuiCreator, 200";
    private static final String TEST_LABEL_OUTPUT = "NewGuiCreator";
    private static final String TEST_TEXTFIELD_INPUT = "    FreeTextField: This is an example of a free textField, 500";
    private static final String TEST_TEXTFIELD_OUTPUT = "This is an example of a free textField";
    private static final String TEST_CHECKBOX_INPUT = "    FreeCheckBox: Please select if this is to be selected, 300";
    private static final String TEST_CHECKBOX_OUTPUT  = "Please select if this is to be selected";
    private static final String TEST_TEXTAREA_INPUT = "    FreeTextArea: The output will be shown below:, 200, 950, 200, false";
    private static final String TEST_TEXTAREA_OUTPUT = "The output will be shown below:";
    private static final String TEST_COMBOBOX_INPUT = "    FreeComboBox: 200, projectNames";
    private static final String TEST_COMBOBOX_OUTPUT = "--Select";
    private static final String TEST_LABELCOMBOBOXPAIR_INPUT = "    FreeLabelComboBoxPair: Please select the project name:, 10, projectNames";
    private static final String TEST_LABELCOMBOBOXPAIR_OUTPUT = "Please select the project name:";
    private static final String TEST_LABELTEXTFIELDPAIR_INPUT = "    FreeLabelTextFieldPair: Please enter the new branch name:, 10";
    private static final String TEST_LABELTEXTFIELDPAIR_OUTPUT = "Please enter the new branch name:";
    private static final String TEST_LABELTEXTBUTTONTRIPLE_INPUT = "    FreeLabelTextButtonTriple: Please enter a file name:, 10, Clear";
    private static final String TEST_LABELTEXTBUTTONTRIPLE_OUTPUT = "Please enter a file name:";
    private static final String TEST_LINE_COLOR = "backgroundcolor: value=235, 255, 255";
    private static final String TEST_LINE_DIM = "dimension: xsize=1000, ysize=900";
    private static final String TEST_LINE_HEADING = "heading: value=Example Dashboard";
    private static final String TEST_LINE_SUBHEADING = "subheading: value=Main Dashboard";

    GuiProperties guiProperties = new GuiProperties();
    GuiScriptParser guiScriptParser;

    @Mock
    MessageHandler messageHandler = mock(MessageHandler.class);

    @Before
    public void setup() {
        guiScriptParser = new GuiScriptParser(guiProperties, messageHandler);
    }

    @Test
    public void testParseMenues_NoMenuesIncluded() {

        ArrayList<String> menues = new ArrayList<>();

        guiScriptParser.parseMenues(menues);

        assertThat(guiProperties.getMenues().size(), is(ARRAY_SIZE_ZERO));
    }

    @Test
    public void testParseMenus_MenuesIncluded() {

        ArrayList<String> menues = new ArrayList<>();
        menues.add(TEST_MENU);

        guiScriptParser.parseMenues(menues);

        assertThat(guiProperties.getMenues().size(), is(ARRAY_SIZE_ONE));
        assertThat(guiProperties.getMenues().get(0), is(TEST_MENU));
    }

    @Test
    public void testParseButtons_NoButtonsIncluded() {

        ArrayList<String> buttons = new ArrayList<>();

        guiScriptParser.parseButtons(buttons);

        assertThat(guiProperties.getButtons().size(), is(ARRAY_SIZE_ZERO));
    }

    @Test
    public void testParseButtons_ButtonsIncluded() {

        ArrayList<String> buttons = new ArrayList<>();
        buttons.add(TEST_BUTTON_INPUT);

        guiScriptParser.parseButtons(buttons);

        assertThat(guiProperties.getButtons().size(), is(ARRAY_SIZE_ONE));
        assertThat(((FreeButton) guiProperties.getButtons().get(0)).getButtonText(), is(TEST_BUTTON_OUTPUT));
    }

    @Test
    public void testParseComponents_NoComponentsIncluded() {

        ArrayList<String> components = new ArrayList<>();

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ZERO));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_Label() {

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_LABEL_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeLabel fl = (FreeLabel) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(fl.getLabelText(), is(TEST_LABEL_OUTPUT));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_TextField() {

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_TEXTFIELD_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeTextField ftf = (FreeTextField) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(ftf.getValue(), is(TEST_TEXTFIELD_OUTPUT));
        assertThat(ftf.empty(), is(false));
        ftf.clearAndFocus();
        assertThat(ftf.getValue(), is(""));
        assertThat(ftf.empty(), is(true));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_TextArea() {

        final String TEST_TEXT = "Blah blah blah";

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_TEXTAREA_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeTextArea fta = (FreeTextArea) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(fta.getLabelText(), is(TEST_TEXTAREA_OUTPUT));
        assertThat(fta.getReadOnly(), is(false));
        assertThat(fta.getXSize(), is(200));
        assertThat(fta.empty(), is (true));
        assertThat(fta.getText(), is (""));
        fta.append(TEST_TEXT);
        assertThat(fta.empty(), is (false));
        assertThat(fta.getText(), is(TEST_TEXT));
        fta.clearTextArea();
        assertThat(fta.empty(), is (true));
        assertThat(fta.getText(), is (""));
        fta.setText(TEST_TEXT);
        assertThat(fta.empty(), is (false));
        assertThat(fta.getText(), is(TEST_TEXT));
        fta.clearTextArea();
        fta.appendNewLine(TEST_TEXT);
        fta.append(TEST_TEXT);
        assertThat(fta.empty(), is (false));
        assertThat(fta.getText(), is(TEST_TEXT + "\n" + TEST_TEXT));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_CheckBox() {

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_CHECKBOX_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeCheckBox fcb = (FreeCheckBox) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(fcb.getLabelText(), is(TEST_CHECKBOX_OUTPUT));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_ComboBoxTests() {

        when(messageHandler.getMessage("filepaths.resourcedir", new String[]{""})).thenReturn("C:/GradleTutorials/NewGUICreator/GuiSourceFiles/");
        when(messageHandler.getMessage("filepaths.combo.ext")).thenReturn(".combo");

        int numItems = 3;
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            items.add("Choice " + (i + 1));
        }

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_COMBOBOX_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            assertThat(e.getMessage(), is("Unable to open file 'C:/GradleTutorials/NewGUICreator/GuiSourceFiles/null/projectNames.combo'"));
        } finally {
            assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ZERO));
        }

        components = new ArrayList<>();
        components.add(TEST_LABELCOMBOBOXPAIR_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            assertThat(e.getMessage(), is("Unable to open file 'C:/GradleTutorials/NewGUICreator/GuiSourceFiles/null/projectNames.combo'"));
        } finally {
            assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ZERO));
        }
    }

    @Test
    public void testParseComponents_ComponentsIncluded_LabelTextFieldPair() {

        final String ALT_LABEL_TEXT = "New label text";
        final String TEST_TEXT = "Blah blah blah";

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_LABELTEXTFIELDPAIR_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeLabelTextFieldPair ftfp = (FreeLabelTextFieldPair) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(ftfp.getLabelText(), is(TEST_LABELTEXTFIELDPAIR_OUTPUT));

        ftfp.updateLabelText(ALT_LABEL_TEXT);
        assertThat(ftfp.getLabelText(), is(ALT_LABEL_TEXT));
        assertThat(ftfp.empty(), is (true));
        assertThat(ftfp.getText(), is(""));
        ftfp.setText(TEST_TEXT);
        assertThat(ftfp.empty(), is (false));
        assertThat(ftfp.getText(), is(TEST_TEXT));
        ftfp.clearTextField();
        assertThat(ftfp.empty(), is (true));
        assertThat(ftfp.getText(), is(""));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_LabelTextFieldButtonTriple() {

        final String ALT_LABEL_TEXT = "New label text";
        final String TEST_TEXT = "Blah blah blah";

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_LABELTEXTBUTTONTRIPLE_INPUT);

        try {
            guiScriptParser.parseComponents(components);
        } catch (ComboBoxItemsFileException e) {
            e.printStackTrace();
        }

        FreeLabelTextButtonTriple ftfp = (FreeLabelTextButtonTriple) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(ftfp.getLabelText(), is(TEST_LABELTEXTBUTTONTRIPLE_OUTPUT));
        assertThat(ftfp.getText(), is (""));
        assertThat(ftfp.empty(), is (true));

        ftfp.updateLabelText(ALT_LABEL_TEXT);
        assertThat(ftfp.getLabelText(), is(ALT_LABEL_TEXT));
        ftfp.setText(TEST_TEXT);
        assertThat(ftfp.getText(), is (TEST_TEXT));
        ftfp.clearTextField();
        assertThat(ftfp.getText(), is (""));
        assertThat(ftfp.empty(), is (true));
        assertThat(ftfp.getButtonText(), is("Clear"));
        ftfp.updateButtonText("Close");
        assertThat(ftfp.getButtonText(), is("Close"));
    }

    @Test
    public void testParseLine_color() {

        String line = TEST_LINE_COLOR;

        guiScriptParser.parseLine(line);

        assertThat(guiProperties.getGuiBackgroundColor(), notNullValue());
        assertThat(guiProperties.getGuiBackgroundColor().getRed(), is(235));
        assertThat(guiProperties.getGuiBackgroundColor().getGreen(), is(255));
        assertThat(guiProperties.getGuiBackgroundColor().getBlue(), is(255));
    }

    @Test
    public void testParseLine_dimesion() {

        String line = TEST_LINE_DIM;

        guiScriptParser.parseLine(line);

        assertThat(guiProperties.getFrameXSize(), is(1000));
        assertThat(guiProperties.getFrameYSize(), is(900));
    }

    @Test
    public void testParseLine_headings() {

        String line = TEST_LINE_HEADING;
        guiScriptParser.parseLine(line);
        line = TEST_LINE_SUBHEADING;
        guiScriptParser.parseLine(line);

        assertThat(guiProperties.getMainHeading(), is("Example Dashboard"));
        assertThat(guiProperties.getSubHeading(), is("Main Dashboard"));
    }
}
