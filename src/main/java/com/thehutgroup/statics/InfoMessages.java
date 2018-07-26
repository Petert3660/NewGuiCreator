package com.thehutgroup.statics;

public class InfoMessages {

    public static String messageBuilder(String javaProjectName, int messNumber) {

        if (messNumber == 0) {
            return "TestGui.java has been successfully copied to the " + javaProjectName + " project";
        } else if (messNumber == 1) {
            return "ERROR - The file, " + javaProjectName + " does not exist in the GUI script source directory - exiting!";
        } else {
            return "";
        }
    }
}
