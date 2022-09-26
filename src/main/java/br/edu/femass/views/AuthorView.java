package br.edu.femass.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorView extends JFrame {
    private JPanel authorPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JList list1;
    private JScrollPane listContainer;
    private JPanel formContainer;
    private JTextField firstNameInput;
    private JTextField secondNameInput;
    private JTextField nationalityInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;


    public AuthorView() {
        initialize();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEditMode(true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                setEditMode(false);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                setEditMode(false);
            }
        });
    }

    public JPanel getAuthorPanel() {
        return this.authorPanel;
    }

    // Private methods

    private void initialize() {
        setEditMode(false);
    }

    private void setEditMode(boolean editing) {
       if(editing) {
           codeInput.setEditable(true);
           firstNameInput.setEditable(true);
           secondNameInput.setEditable(true);
           nationalityInput.setEditable(true);

           addButton.setVisible(false);
           cancelButton.setVisible(true);
           saveButton.setVisible(true);
       }
       else {
           codeInput.setEditable(false);
           firstNameInput.setEditable(false);
           secondNameInput.setEditable(false);
           nationalityInput.setEditable(false);

           addButton.setVisible(true);
           cancelButton.setVisible(false);
           saveButton.setVisible(false);
       }
    }

    private void clearFields() {
        codeInput.setText(null);
        firstNameInput.setText(null);
        secondNameInput.setText(null);
        nationalityInput.setText(null);
    }
}
