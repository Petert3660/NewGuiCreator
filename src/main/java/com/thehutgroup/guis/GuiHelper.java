package com.thehutgroup.guis;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomsonp on 04/01/2017.
 */
public class GuiHelper {

    public static void showFrame(JFrame f) {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = f.getSize();
        f.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        f.setVisible(true);
    }
}
