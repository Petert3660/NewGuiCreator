package guiScriptParser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.thehutgroup.guiScriptParser.GuiScriptParser;
import com.thehutgroup.guis.GuiProperties;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TestGuiScriptParser {

    private static final String TEST_MENU = "    [File: Open, Close, -separator, Exit-implement]";

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

        assertThat(guiProperties.getMenues().size(), is(0));
    }

    @Test
    public void testParseMenus_MenuesIncluded() {

        ArrayList<String> menues = new ArrayList<>();
        menues.add(TEST_MENU);

        guiScriptParser.parseMenues(menues);

        assertThat(guiProperties.getMenues().size(), is(1));
        assertThat(guiProperties.getMenues().get(0), is(TEST_MENU));
    }

    @Test
    public void testParseComponents() {

    }
}
