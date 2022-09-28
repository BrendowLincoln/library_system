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
    private JButton deleteButton;

    private AuthorDao _authorDao;
    private Boolean _isNew = true;


    public AuthorView() {
        _authorDao = new AuthorDao();

        initialize();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _isNew = true;
                setEditMode(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _authorDao.delete(Integer.parseInt(codeInput.getText()));
                    clearFields();
                    setEditMode(false);
                    updateList();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
                if(hasEmptyFields()) {
                    JOptionPane.showMessageDialog(null, "Preenchimento dos campos é obrigatório.");
                    return;
                }
                save();
            }
        });
        authorList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;

                Author selectedAuthor = (Author) authorList.getSelectedValue();
                if(selectedAuthor == null) return;

                codeInput.setText(selectedAuthor.getCode().toString());
                firstNameInput.setText(selectedAuthor.getName());
                secondNameInput.setText(selectedAuthor.getSecondName());
                nationalityInput.setText(selectedAuthor.getNationality());

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

            if(!_isNew) {
                deleteButton.setVisible(true);
            }
        }
        else {
            codeInput.setEditable(false);
            firstNameInput.setEditable(false);
            secondNameInput.setEditable(false);
            nationalityInput.setEditable(false);

            addButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            deleteButton.setVisible(false);
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
            List<Author> clientes = _authorDao.getAll();
            authorList.setListData(clientes.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasEmptyFields() {
        return firstNameInput.getText().isEmpty() ||
            secondNameInput.getText().isEmpty() ||
            nationalityInput.getText().isEmpty();
    }

    private void save() {
        if(_isNew) {
            create();
        } else {
            change();
        }
    }

    private void create() {
        try {
            Author newAuthor = new Author(
                    _authorDao.getNextCode(),
                    firstNameInput.getText(),
                    secondNameInput.getText(),
                    nationalityInput.getText()
            );

            _authorDao.save(newAuthor);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void change() {
        try {
            Author updatedAuthor = new Author(
                    Integer.parseInt(codeInput.getText()),
                    firstNameInput.getText(),
                    secondNameInput.getText(),
                    nationalityInput.getText()
            );

            _authorDao.update(updatedAuthor);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
