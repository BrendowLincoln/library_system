package br.edu.femass;

import br.edu.femass.daos.AuthorDao;
import br.edu.femass.models.Author;

import javax.swing.*;
import java.awt.*;

public class Startup {

    public static void main(String[] args) {
        final ApplicationView application = new ApplicationView();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.95);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.8);

        ImageIcon img;

        JFrame applicationFrame = new JFrame("Livraría FeMASS");
        applicationFrame.setContentPane(application.getApplicationPanel());
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.pack();
        applicationFrame.setSize(screenWidth, screenHeight);
        applicationFrame.setVisible(true);
        applicationFrame.setLocation(
            screenSize.width/2- applicationFrame.getSize().width/2,
            screenSize.height/2-applicationFrame.getSize().height/2
        );
    }
}
