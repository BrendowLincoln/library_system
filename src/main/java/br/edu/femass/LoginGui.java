package br.edu.femass;

import br.edu.femass.daos.UserDao;
import br.edu.femass.gui.CopyGui;
import br.edu.femass.models.User;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.UserType;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGui {
    private JPanel loginPanel;
    private JLabel logoField;
    private JTextField userNameInput;
    private JTextField passwordInput;
    private JButton submitButton;

    private UserDao _userDao;


    public LoginGui() {
        _userDao = new UserDao();

        initialize();
        loginPanel.addComponentListener(new ComponentAdapter() {
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
    }

    public JPanel getLoginPanel() {
        return loginPanel;
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

    private void openApplicationDialog(User user) {
        ApplicationGui application = new ApplicationGui(user);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.6);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.8);

        JFrame applicationFrame = new JFrame("FeMASS - Biblioteca Universitária");
        applicationFrame.setContentPane(application.getApplicationPanel());
        applicationFrame.pack();
        applicationFrame.setSize(screenWidth, screenHeight);
        applicationFrame.setVisible(true);
        applicationFrame.setLocation(
                screenSize.width/2- applicationFrame.getSize().width/2,
                screenSize.height/2-applicationFrame.getSize().height/2
        );
        applicationFrame.setResizable(false);
    }

    private void submit() {
        try {

            User user = _userDao.getByUseranmeAndPassword(userNameInput.getText(), passwordInput.getText());


            if(user == null) {
                JOptionPane.showMessageDialog(null, GlobalExceptionMessage.INVALID_LOGIN);
                return;
            }

            openApplicationDialog(user);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.INVALID_LOGIN);
            return;
        }
    }
}
