package br.edu.femass.gui;

import br.edu.femass.daos.BookDao;
import br.edu.femass.models.Book;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JComboBox authorCombo;
    private JButton addCoopyButton;
    private JComboBox copyCombo;

    private BookDao _bookDao;
    private Boolean _isNew = true;

    public BookGui() {
        _bookDao = new BookDao();

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
                    JOptionPane.showMessageDialog(null, "Preenchimento dos campos é obrigatório.");
                    return;
                }
                save();
            }
        });
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;

                Book selectedBook = (Book) bookList.getSelectedValue();
                if(selectedBook == null) return;

                codeInput.setText(selectedBook.getCode().toString());
                titleInput.setText(selectedBook.getTitle());

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
    }

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);

        if(editing) {
            titleInput.setEditable(true);
            authorCombo.setEnabled(true);
            copyCombo.setEnabled(true);

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
            authorCombo.setEnabled(false);
            copyCombo.setEnabled(false);

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
        authorCombo.setSelectedItem(null);
        copyCombo.setSelectedItem(null);
        bookList.clearSelection();
    }

    private void updateList() {
        try {
            List<Book> clientes = _bookDao.getAll();
            bookList.setListData(clientes.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasEmptyFields() {
        return titleInput.getText().isEmpty() ||
                authorCombo.getSelectedItem().toString().isEmpty() ||
                copyCombo.getSelectedItem().toString().isEmpty();
    }

    private void save() {
        if(_isNew) {
            create();
        } else {
            change();
        }
    }

    private void create() {

    }

    private void change() {

    }


    private void openCopiesFormDialog() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.4);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.6);

        JDialog frame = new JDialog(new Frame(), true);
        CopyGui gui = new CopyGui();
        frame.setContentPane(gui.getCopiesPanel());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setTitle((_isNew ? "Novo livro" : titleInput.getText()) + " - Cópias");
        frame.setLocation(
                screenSize.width/2- frame.getSize().width/2,
                screenSize.height/2-frame.getSize().height/2
        );

        frame.setVisible(true);
        frame.pack();
    }


}
