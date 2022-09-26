package br.edu.femass;

import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.views.AuthorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationView extends JFrame {
    private JPanel applicationPanel;
    private JPanel headerContainer;
    private JPanel mainContainer;
    private JLabel logoField;
    private JLabel applicationLabel;
    private JPanel menuContainer;
    private JButton employeeButton;
    private JButton readerButton;
    private JButton loanButton;
    private JButton authorButton;
    private JButton bookButton;
    private JPanel buttonMenuContainer;


    public ApplicationView()  {

        setLogoImage();

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        authorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthorView authorview = new AuthorView();
                mainContainer.removeAll();
                mainContainer.setLayout(new GridBagLayout());
                authorview.getAuthorPanel().setSize(mainContainer.getSize());
                authorview.getAuthorPanel().setPreferredSize(mainContainer.getSize());

                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.CENTER;
                mainContainer.add(authorview.getAuthorPanel(), c);
                mainContainer.validate();
                mainContainer.repaint();
            }
        });

        readerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getApplicationPanel() {
        return this.applicationPanel;
    }


    private void setLogoImage() {
        try {
            ImageIcon logo = new ImageIcon(new ImageIcon(GlobalConstants.IMAGES_DIRECTORY_PATH + "femass_logo.png").getImage().getScaledInstance(85, 85, Image.SCALE_DEFAULT));
            logoField.setIcon(logo);
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    }
}
