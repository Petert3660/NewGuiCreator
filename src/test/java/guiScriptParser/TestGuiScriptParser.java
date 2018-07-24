package guiScriptParser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.thehutgroup.guiScriptParser.GuiScriptParser;
import com.thehutgroup.guicomponents.FreeButton;
import com.thehutgroup.guicomponents.FreeCheckBox;
import com.thehutgroup.guicomponents.FreeLabel;
import com.thehutgroup.guicomponents.FreeTextArea;
import com.thehutgroup.guicomponents.FreeTextField;
import com.thehutgroup.guis.GuiProperties;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

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

        GuiProperties guiProperties = new GuiProperties();
    GuiScriptParser guiScriptParser;

    @Before
    public void setup() {
        guiScriptParser = new GuiScriptParser(guiProperties);
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

        guiScriptParser.parseComponents(components);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ZERO));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_Label() {

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_LABEL_INPUT);

        guiScriptParser.parseComponents(components);

        FreeLabel fl = (FreeLabel) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(fl.getLabelText(), is(TEST_LABEL_OUTPUT));
    }

    @Test
    public void testParseComponents_ComponentsIncluded_TextField() {

        ArrayList<String> components = new ArrayList<>();
        components.add(TEST_TEXTFIELD_INPUT);

        guiScriptParser.parseComponents(components);

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

        guiScriptParser.parseComponents(components);

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

        guiScriptParser.parseComponents(components);

        FreeCheckBox fcb = (FreeCheckBox) guiProperties.getComponents().get(0);

        assertThat(guiProperties.getComponents().size(), is(ARRAY_SIZE_ONE));
        assertThat(fcb.getLabelText(), is(TEST_CHECKBOX_OUTPUT));
    }
}
