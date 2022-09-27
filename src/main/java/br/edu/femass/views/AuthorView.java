package br.edu.femass.views;

import br.edu.femass.daos.AuthorDao;
import br.edu.femass.models.Author;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AuthorView extends JFrame {
    private JPanel authorPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JList authorList;
    private JScrollPane listContainer;
    private JPanel formContainer;
    private JTextField firstNameInput;
    private JTextField secondNameInput;
    private JTextField nationalityInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;

    private AuthorDao authorDao;


    public AuthorView() {
        authorDao = new AuthorDao();

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
                try {
                    Author newAuthor = new Author(
                        firstNameInput.getText(),
                        secondNameInput.getText(),
                        nationalityInput.getText()
                    );
                    authorDao.save(newAuthor);

                    clearFields();
                    setEditMode(false);
                    updateList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        authorList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Author author = (Author) authorList.getSelectedValue();
                if(author == null) return;

                codeInput.setText(author.getCode().toString());
                firstNameInput.setText(author.getName());
                secondNameInput.setText(author.getSecondName());
                nationalityInput.setText(author.getNationality());

                setEditMode(true);
            }
        });
    }

    public JPanel getAuthorPanel() {
        return this.authorPanel;
    }

    // Private methods
    private void initialize() {
        setEditMode(false);
        updateList();
    }

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);

        if(editing) {
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

    private void updateList() {
        try {
            List<Author> clientes = authorDao.getAll();
            authorList.setListData(clientes.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
