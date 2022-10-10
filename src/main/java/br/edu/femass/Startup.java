package br.edu.femass;

import javax.swing.*;
import java.awt.*;

public class Startup {

    public static void main(String[] args) {
        final ApplicationGui application = new ApplicationGui();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.6);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.8);

        JFrame applicationFrame = new JFrame("FeMASS - Biblioteca Universitária");
        applicationFrame.setContentPane(application.getApplicationPanel());
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.pack();
        applicationFrame.setSize(screenWidth, screenHeight);
        applicationFrame.setVisible(true);
        applicationFrame.setLocation(
            screenSize.width/2- applicationFrame.getSize().width/2,
            screenSize.height/2-applicationFrame.getSize().height/2
        );
        applicationFrame.setResizable(false);
    }
}
