package br.edu.femass.gui;

import br.edu.femass.daos.BookDao;
import br.edu.femass.models.Book;
import br.edu.femass.models.Copy;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectCopiesGui {
    private JPanel selectCopiesPanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList selectedCopiesList;
    private JPanel formContainer;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox booksCombo;
    private JComboBox availableCopiesCombo;

    private BookDao _bookDao;

    private Boolean _isNew = true;
    private List<Copy> _currentLoanCopies;

    public SelectCopiesGui(List<Copy> currentLoanCopies) {
        _bookDao = new BookDao();
        _currentLoanCopies = currentLoanCopies;

        initialize();

        selectedCopiesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    Copy selectedCopy = (Copy) selectedCopiesList.getSelectedValue();
                    Book selectedBook = _bookDao.getByCode(selectedCopy.getBookCode());


                    booksCombo.setSelectedItem(selectedBook);
                    availableCopiesCombo.setSelectedItem(selectedCopy);

                    setEditMode(true);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _isNew = true;
                _currentLoanCopies = new ArrayList<Copy>();
                setEditMode(true);
                updateBooksCombo();
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


                save();
                clearFields();
                setEditMode(false);
            }
        });


        booksCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAvailableCopiesCombo();
            }
        });
    }

    public JPanel getSelectCopiesPanel() {
        return selectCopiesPanel;
    }

    public List<Copy> getCopies() {
        return this._currentLoanCopies;
    }


    //Private methods
    private void initialize() {
        setEditMode(false);
        updateList();
    }

    private void setEditMode(boolean editing) {

        if(editing) {
            booksCombo.setEnabled(true);
            availableCopiesCombo.setEnabled(true);

            addButton.setVisible(false);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);


            if(!_isNew) {
                deleteButton.setVisible(true);
                saveButton.setVisible(false);
            }
        }
        else {
            booksCombo.setEnabled(false);
            availableCopiesCombo.setEnabled(false);

            addButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void clearFields() {
        booksCombo.setSelectedItem(null);
        availableCopiesCombo.setSelectedItem(null);
    }

    private void updateList() {
        try {
            List<Copy> copiesList = this._currentLoanCopies;
            selectedCopiesList.setListData(copiesList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateBooksCombo() {
        booksCombo.removeAllItems();
        try {
            List<Book> books = _bookDao.getAll();
            for(Book book: books) {
                booksCombo.addItem(book);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.COULD_NOT_LOAD_BOOKS_LIST);
        }
    }

    private void updateAvailableCopiesCombo() {
        availableCopiesCombo.removeAllItems();
        try {
            Book selectedBook = (Book) booksCombo.getSelectedItem();
            List<Copy> availableCopies = selectedBook.getCopies().stream().filter(copy -> copy.getIsFixed() == false &&  copy.getLoaned() == false).collect(Collectors.toList());


            for(Copy copy: availableCopies) {
                availableCopiesCombo.addItem(copy);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.COULD_NOT_LOAD_COPIES_LIST);
        }
    }

    private void save() {
        if(booksCombo.getSelectedItem() == null || availableCopiesCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.FILLING_IN_THE_FIELDS_IS_MANDATORY);
            return;
        }

        Copy selectedCopy = (Copy) availableCopiesCombo.getSelectedItem();

        this._currentLoanCopies.add(selectedCopy);

        clearFields();
        setEditMode(false);
        updateList();
    }


}
