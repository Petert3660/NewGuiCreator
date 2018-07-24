package guiScriptParser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.thehutgroup.guiScriptParser.GuiScriptParser;
import com.thehutgroup.guicomponents.FreeButton;
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
    private static final String TEST_LABEL_INPUT = "    [Exit-implement]";
    private static final String TEST_LABEL_OUTPUT = "Exit-implement";

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

    }

    @Test
    public void testParseComponents_ComponentsIncluded_TextField() {

    }

    @Test
    public void testParseComponents_ComponentsIncluded_TextArea() {

    }

    @Test
    public void testParseComponents_ComponentsIncluded_CheckBox() {

    }
}
