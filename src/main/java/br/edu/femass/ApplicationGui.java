package br.edu.femass;

import br.edu.femass.gui.LoanGui;
import br.edu.femass.gui.ReaderGui;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.gui.AuthorGui;
import br.edu.femass.utils.Menus;
import br.edu.femass.gui.BookGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationGui extends JFrame {
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

    public ApplicationGui()  {
        initialize();


        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuStateController(Menus.BOOKS);
            }
        });

        authorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuStateController(Menus.AUTHORS);
            }
        });

        readerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuStateController(Menus.READERS);
            }
        });

        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuStateController(Menus.LOANS);
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuStateController(Menus.EMPLOYEES);
            }
        });
    }

    public JPanel getApplicationPanel() {
        return this.applicationPanel;
    }

    //Private Methods
    private void initialize() {
        setLogoImage();
    }


    private void setLogoImage() {
        try {
            ImageIcon logo = new ImageIcon(new ImageIcon(GlobalConstants.IMAGES_DIRECTORY_PATH + "femass_logo.png").getImage().getScaledInstance(85, 85, Image.SCALE_DEFAULT));
            logoField.setIcon(logo);
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    }

   private void menuStateController(Menus selectedMenu) {
        switch (selectedMenu) {
            case BOOKS:
                //Selected Button
                bookButton.setBackground(GlobalConstants.WHITE);
                bookButton.setForeground(GlobalConstants.GRAY);

                //Others Button
                authorButton.setBackground(GlobalConstants.LIGHT_GRAY);
                authorButton.setForeground(GlobalConstants.GRAY);
                readerButton.setBackground(GlobalConstants.LIGHT_GRAY);
                readerButton.setForeground(GlobalConstants.GRAY);
                loanButton.setBackground(GlobalConstants.LIGHT_GRAY);
                loanButton.setForeground(GlobalConstants.GRAY);
                employeeButton.setBackground(GlobalConstants.LIGHT_GRAY);
                employeeButton.setForeground(GlobalConstants.GRAY);

                BookGui bookView = new BookGui();
                configureMainContent(bookView.getBookPanel());

                break;

            case AUTHORS:
                //Selected Button
                authorButton.setBackground(GlobalConstants.WHITE);
                authorButton.setForeground(GlobalConstants.GRAY);

                //Others Button
                bookButton.setBackground(GlobalConstants.LIGHT_GRAY);
                bookButton.setForeground(GlobalConstants.GRAY);
                readerButton.setBackground(GlobalConstants.LIGHT_GRAY);
                readerButton.setForeground(GlobalConstants.GRAY);
                loanButton.setBackground(GlobalConstants.LIGHT_GRAY);
                loanButton.setForeground(GlobalConstants.GRAY);
                employeeButton.setBackground(GlobalConstants.LIGHT_GRAY);
                employeeButton.setForeground(GlobalConstants.GRAY);

                AuthorGui authorView = new AuthorGui();
                configureMainContent(authorView.getAuthorPanel());
                break;

            case READERS:
                //Selected Button
                readerButton.setBackground(GlobalConstants.WHITE);
                readerButton.setForeground(GlobalConstants.GRAY);

                //Others Button
                bookButton.setBackground(GlobalConstants.LIGHT_GRAY);
                bookButton.setForeground(GlobalConstants.GRAY);
                authorButton.setBackground(GlobalConstants.LIGHT_GRAY);
                authorButton.setForeground(GlobalConstants.GRAY);
                loanButton.setBackground(GlobalConstants.LIGHT_GRAY);
                loanButton.setForeground(GlobalConstants.GRAY);
                employeeButton.setBackground(GlobalConstants.LIGHT_GRAY);
                employeeButton.setForeground(GlobalConstants.GRAY);

                ReaderGui readerView = new ReaderGui();
                configureMainContent(readerView.getAuthorPanel());

                break;

            case LOANS:
                //Selected Button
                loanButton.setBackground(GlobalConstants.WHITE);
                loanButton.setForeground(GlobalConstants.GRAY);

                //Others Button
                bookButton.setBackground(GlobalConstants.LIGHT_GRAY);
                bookButton.setForeground(GlobalConstants.GRAY);
                authorButton.setBackground(GlobalConstants.LIGHT_GRAY);
                authorButton.setForeground(GlobalConstants.GRAY);
                readerButton.setBackground(GlobalConstants.LIGHT_GRAY);
                readerButton.setForeground(GlobalConstants.GRAY);
                employeeButton.setBackground(GlobalConstants.LIGHT_GRAY);
                employeeButton.setForeground(GlobalConstants.GRAY);

                LoanGui loanView = new LoanGui();
                configureMainContent(loanView.getLoanPanel());
                break;

            case EMPLOYEES:
                //Selected Button
                employeeButton.setBackground(GlobalConstants.WHITE);
                employeeButton.setForeground(GlobalConstants.GRAY);

                //Others Button
                bookButton.setBackground(GlobalConstants.LIGHT_GRAY);
                bookButton.setForeground(GlobalConstants.GRAY);
                authorButton.setBackground(GlobalConstants.LIGHT_GRAY);
                authorButton.setForeground(GlobalConstants.GRAY);
                readerButton.setBackground(GlobalConstants.LIGHT_GRAY);
                readerButton.setForeground(GlobalConstants.GRAY);
                loanButton.setBackground(GlobalConstants.LIGHT_GRAY);
                loanButton.setForeground(GlobalConstants.GRAY);
                break;
        }
   }

   private void configureMainContent(JPanel panel) {
       mainContainer.removeAll();
       mainContainer.setLayout(new GridBagLayout());
       panel.setSize(mainContainer.getSize());
       panel.setPreferredSize(mainContainer.getSize());

       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.CENTER;
       mainContainer.add(panel, c);
       mainContainer.validate();
       mainContainer.repaint();
   }
}
