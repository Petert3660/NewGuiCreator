package com.thehutgroup.guicomponents;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by thomsonp on 04/01/2017.
 */
@Component
public class GuiProperties {

    // Owner
    private String owner;

    // Gui Dimensions
    private int frameXSize;
    private int frameYSize;

    // Gui Background Color
    private Color guiBackgroundColor;

    // Gui Title and Headings
    private String mainHeading;
    private String subHeading;

    // Gui Components
    private ArrayList<Object> components = new ArrayList<Object>();
    private ArrayList<Boolean> panelComponent = new ArrayList<Boolean>();

    // Gui Buttons
    private ArrayList<Object> buttons = new ArrayList<Object>();

    // Gui RadioButtons
    private ArrayList<Object> radioButtons = new ArrayList<Object>();

    private ArrayList<String> menues = new ArrayList<String>();

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getFrameXSize() {
        return frameXSize;
    }

    public void setFrameXSize(int frameXSize) {
        this.frameXSize = frameXSize;
    }

    public int getFrameYSize() {
        return frameYSize;
    }

    public void setFrameYSize(int frameYSize) {
        this.frameYSize = frameYSize;
    }

    public Color getGuiBackgroundColor() {
        return guiBackgroundColor;
    }

    public void setGuiBackgroundColor(Color guiBackgroundColor) {
        this.guiBackgroundColor = guiBackgroundColor;
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public void setMainHeading(String mainHeading) {
        this.mainHeading = mainHeading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public ArrayList<Object> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Object> components) {
        this.components = components;
    }

    public ArrayList<Object> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<Object> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<Boolean> getPanelComponent() {
        return panelComponent;
    }

    public void setPanelComponent(ArrayList<Boolean> panelComponent) {
        this.panelComponent = panelComponent;
    }

    public ArrayList<Object> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(ArrayList<Object> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public ArrayList<String> getMenues() {
        return menues;
    }

    public void setMenues(ArrayList<String> menues) {
        this.menues = menues;
    }
}
