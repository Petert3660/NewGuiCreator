package com.thehutgroup;

import com.thehutgroup.createdgui.BuildVersion;
import com.thehutgroup.createdgui.MainGui;
import com.thehutgroup.guis.GuiHelper;
import com.thehutgroup.guis.GuiProperties;
import com.thehutgroup.messages.MessageHandler;
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

    @Autowired
    MessageHandler messageHandler;

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
        System.out.println("New GUI Creator Running, Version: " + BuildVersion.getBuildVersion());
        System.out.println(messageHandler.getMessage("messages.test", new String[]{"by the way"}));
        System.out.println(messageHandler.getMessage("constants.test"));
        MainGui tmg = new MainGui(guiProperties, messageHandler);
        GuiHelper.showFrame(tmg);
    }
}