package com.thehutgroup.guicomponents;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TestComponents {

    private static final String ITEM_0 = "--Select";
    private static final String ITEM_1 = "Item One";
    private static final String ITEM_2 = "Item Two";
    private static final String ITEM_3 = "Item Three";

    ArrayList<String> items;

    @Before
    public void setup() {
        items = new ArrayList<>();

        items.add(ITEM_0);
        items.add(ITEM_1);
        items.add(ITEM_2);
        items.add(ITEM_3);
    }

    @Test
    public void testComponenents_comboBox() {

        String[] NEW_ITEMS = {"--Select", "New_Item_1", "New_Item_2"};

        FreeComboBox fcb = new FreeComboBox(0, 0, 0, 0, items);

        assertThat(fcb.getItemCount(), is(4));
        assertThat(fcb.getItem(0), is(ITEM_0));
        assertThat(fcb.getItem(1), is(ITEM_1));
        assertThat(fcb.getItem(2), is(ITEM_2));
        assertThat(fcb.getItem(3), is(ITEM_3));
        fcb.clearComboBox();
        assertThat(fcb.getItemCount(), is(4));
        assertThat(fcb.getSelectedItem(), is(ITEM_0));

        items = new ArrayList<>();
        for (int i = 0; i < NEW_ITEMS.length; i++) {
            items.add(NEW_ITEMS[i]);
        }
        fcb.repopulateComboBox(items);

        assertThat(fcb.getItemCount(), is(3));
        assertThat(fcb.getItem(0), is(NEW_ITEMS[0]));
        assertThat(fcb.getItem(1), is(NEW_ITEMS[1]));
        assertThat(fcb.getItem(2), is(NEW_ITEMS[2]));
    }
}
