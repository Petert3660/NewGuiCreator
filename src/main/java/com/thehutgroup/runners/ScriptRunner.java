package com.thehutgroup.runners;

import java.io.IOException;

public class ScriptRunner implements Runnable {

    private String filename;
    private String project;
    private String branch;

    public ScriptRunner(String file, String proj, String bra) {
        filename = file;
        project = proj;
        branch = bra;
    }

    @Override
    public void run() {
        try {
            String command = "C:\\GradleTutorials\\NewGUICreator\\src\\main\\resources\\" + filename + ".bat " + project + " " + branch;
            Process buildRun = Runtime.getRuntime().exec(command);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
