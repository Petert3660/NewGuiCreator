package com.thehutgroup;

import com.thehutgroup.createdgui.MainGui;
import com.thehutgroup.guicomponents.GuiProperties;
import com.thehutgroup.guis.GuiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Peter Thomson on 13/04/2018.
 */
@SuppressWarnings("ALL")
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String PROPS_FILENAME = "application";
    private static final String SERVER_PORT_PROPERTY = "server.port";

    @Autowired
    GuiProperties guiProperties;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
            testGui();
    }

    private void testGui() {
        MainGui tmg = new MainGui(guiProperties);
        GuiHelper.showFrame(tmg);
    }
}