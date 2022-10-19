package br.edu.femass;

import br.edu.femass.models.User;
import br.edu.femass.utils.UserType;

import javax.swing.*;
import java.awt.*;

public class Startup {

    public static void main(String[] args) {
        final LoginGui loginGui = new LoginGui();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.3);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.3);

        JFrame applicationFrame = new JFrame("FeMASS - Biblioteca Universitária - Login");
        applicationFrame.setContentPane(loginGui.getLoginPanel());
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
