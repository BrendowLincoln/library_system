package br.edu.femass;

import br.edu.femass.utils.GlobalConstants;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;

public class ApplicationView {
    private JPanel applicationPanel;
    private JPanel hederContainer;
    private JPanel menuContainer;
    private JPanel mainContainer;
    private JLabel logoField;


    public ApplicationView() {
        System.out.println("Inicializando o componente");
        setLogoImage();
    }

    public JPanel getApplicationPanel() {
        return this.applicationPanel;
    }


    private void setLogoImage() {
        try {
            ImageIcon logo = new ImageIcon(GlobalConstants.IMAGES_DIRECTORY_PATH + "femass_logo.png");
            logoField.setIcon(logo);
            logoField.setSize(106, 75);
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    }
}
