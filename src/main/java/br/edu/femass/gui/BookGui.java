package br.edu.femass.gui;

import br.edu.femass.daos.AuthorDao;
import br.edu.femass.daos.BookDao;
import br.edu.femass.models.Author;
import br.edu.femass.models.Book;
import br.edu.femass.models.Copy;
import br.edu.femass.utils.Nationality;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookGui {
    private JPanel bookPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList bookList;
    private JPanel formContainer;
    private JTextField titleInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox authorsCombo;
    private JButton addCoopyButton;
    private JComboBox copiesCombo;

    private BookDao _bookDao;
    private AuthorDao _authorDao;
    private Boolean _isNew = true;
    private List<Copy> _currentCopies = new ArrayList<Copy>();

    public BookGui() {
        _bookDao = new BookDao();
        _authorDao = new AuthorDao();

        initialize();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _isNew = true;
                _currentCopies = new ArrayList<Copy>();
                setEditMode(true);
                updateAuthorsCombo();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _bookDao.delete(Long.parseLong(codeInput.getText()));
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
                    JOptionPane.showMessageDialog(null, GlobalExceptionMessage.FILLING_IN_THE_FIELDS_IS_MANDATORY);
                    return;
                }
                save();
            }
        });
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;
                updateAuthorsCombo();

                Book selectedBook = (Book) bookList.getSelectedValue();
                if(selectedBook == null) return;

                codeInput.setText(selectedBook.getCode().toString());
                titleInput.setText(selectedBook.getTitle());
                authorsCombo.setSelectedItem(selectedBook.getAuthor());
                _currentCopies = selectedBook.getCopies();

                updateCopiesCombo(selectedBook.getCopies());
                setEditMode(true);
            }
        });
        addCoopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCopiesFormDialog();
            }
        });
    }

    public JPanel getBookPanel() {
        return this.bookPanel;
    }


    // Private methods
    private void initialize() {
        setEditMode(false);
        updateList();
    }

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);

        if(editing) {
            titleInput.setEditable(true);
            authorsCombo.setEnabled(true);
            copiesCombo.setEnabled(true);
            copiesCombo.setEditable(false);

            addButton.setVisible(false);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addCoopyButton.setVisible(true);


            if(!_isNew) {
                deleteButton.setVisible(true);
            }
        }
        else {
            titleInput.setEditable(false);
            authorsCombo.setEnabled(false);
            copiesCombo.setEnabled(false);

            addButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            deleteButton.setVisible(false);
            addCoopyButton.setVisible(false);
        }
    }

    private void clearFields() {
        codeInput.setText(null);
        titleInput.setText(null);
        authorsCombo.removeAllItems();
        copiesCombo.removeAllItems();
        bookList.clearSelection();
    }

    private void updateList() {
        try {
            List<Book> books = _bookDao.getAll();
            bookList.setListData(books.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCopiesCombo(List<Copy> copiesFromBookSelected) {
        copiesCombo.removeAllItems();
        try {
            for (Copy copy: copiesFromBookSelected) {
                copiesCombo.addItem(copy);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.COULD_NOT_LOAD_COPIES_LIST);
        }
    }

    private void updateAuthorsCombo() {
        authorsCombo.removeAllItems();
        try {
            List<Author> authors = _authorDao.getAll();
            for(Author author: authors) {
                authorsCombo.addItem(author);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.COULD_NOT_LOAD_AUTHORS_LIST);
        }
    }

    private boolean hasEmptyFields() {
        return titleInput.getText().isEmpty() ||
                authorsCombo.getSelectedItem().toString().isEmpty() ||
                copiesCombo.getItemCount() <= 0;
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
            Author authorSelected = (Author) authorsCombo.getSelectedItem();

            Book newBook = new Book(
                    _bookDao.getNextCode(),
                    titleInput.getText(),
                    authorSelected,
                    _currentCopies
            );
            _bookDao.save(newBook);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void change() {
        try {

            Book updatedAuthor = new Book(
                    Long.parseLong(codeInput.getText()),
                    titleInput.getText(),
                    (Author) authorsCombo.getSelectedItem(),
                    _currentCopies
            );

            _bookDao.update(updatedAuthor);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    private void openCopiesFormDialog() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.57);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.4);

        JDialog frame = new JDialog(new Frame(), true);
        CopyGui copyGui = new CopyGui(_currentCopies);
        frame.setContentPane(copyGui.getCopiesPanel());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setTitle((titleInput.getText().isBlank() ? "Novo livro" : titleInput.getText()) + " - Cópias");
        frame.setLocation(
                screenSize.width/2- frame.getSize().width/2,
                screenSize.height/2-frame.getSize().height/2
        );

        frame.setVisible(true);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                _currentCopies = copyGui.getCopies();
                updateCopiesCombo(_currentCopies);
            }

        });
    }


}
