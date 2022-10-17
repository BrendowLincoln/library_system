package br.edu.femass.gui;

import br.edu.femass.daos.ReaderDao;
import br.edu.femass.models.Author;
import br.edu.femass.models.Reader;
import br.edu.femass.models.Student;
import br.edu.femass.models.Teacher;
import br.edu.femass.utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static br.edu.femass.utils.ReaderType.STUDENT;
import static br.edu.femass.utils.ReaderType.TEACHER;


public class ReaderGui {
    private JPanel readerPanel;
    private JList readerList;
    private JTextField nameInput;
    private JTextField streetInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox countryCombo;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JPanel formContainer;
    private JTextField numberAddressInput;
    private JTextField neighborhoodInput;
    private JTextField stateInput;
    private JTextField areaCodeInput;
    private JComboBox readerTypeCombo;
    private JTextField numberInput;
    private JScrollPane formContainerScroll;
    private JTextField registerInput;
    private JTextField subjectInput;
    private JLabel registerLabel;
    private JLabel subjectLabel;
    private JTextField cityInput;

    private ReaderDao _readerDao;
    private Boolean _isNew = true;


    public ReaderGui() {
        _readerDao = new ReaderDao();

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
                    _readerDao.delete(Long.parseLong(codeInput.getText()));
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
        readerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;

                Reader selectedReader = (Reader) readerList.getSelectedValue();
                if(selectedReader == null) return;

                codeInput.setText(selectedReader.getCode().toString());
                nameInput.setText(selectedReader.getName());
                streetInput.setText(selectedReader.getAddress().getStreet());
                numberAddressInput.setText(selectedReader.getAddress().getNumber().toString());
                neighborhoodInput.setText(selectedReader.getAddress().getNeighborhood());
                cityInput.setText(selectedReader.getAddress().getCity());
                stateInput.setText(selectedReader.getAddress().getState());
                countryCombo.setSelectedItem(selectedReader.getAddress().getCountry());
                areaCodeInput.setText(selectedReader.getTelephone().getAreaCode().toString());
                numberInput.setText(selectedReader.getTelephone().getNumber().toString());
                readerTypeCombo.setSelectedItem(selectedReader.getReaderType());


                setEditMode(true);
            }
        });


        readerTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchFieldByReaderType((ReaderType) readerTypeCombo.getSelectedItem());
            }
        });
    }

    public JPanel getAuthorPanel() {
        return this.readerPanel;
    }

    // Private methods
    private void initialize() {
        setEditMode(false);
        updateList();
        updateContryCombo();
        updateReaderTypeCombo();
        switchFieldByReaderType((ReaderType) readerTypeCombo.getSelectedItem());
    }

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);

        if(editing) {
            nameInput.setEditable(true);
            streetInput.setEditable(true);
            numberAddressInput.setEditable(true);
            neighborhoodInput.setEditable(true);
            cityInput.setEditable(true);
            stateInput.setEditable(true);
            countryCombo.setEnabled(true);
            areaCodeInput.setEditable(true);
            numberInput.setEditable(true);
            readerTypeCombo.setEnabled(true);


            addButton.setVisible(false);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);

            if(!_isNew) {
                deleteButton.setVisible(true);
            }
        }
        else {
            nameInput.setEditable(false);
            streetInput.setEditable(false);
            numberAddressInput.setEditable(false);
            neighborhoodInput.setEditable(false);
            cityInput.setEditable(false);
            stateInput.setEditable(false);
            countryCombo.setEnabled(false);
            areaCodeInput.setEditable(false);
            numberInput.setEditable(false);
            readerTypeCombo.setEnabled(false);

            addButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void clearFields() {
        codeInput.setText(null);
        nameInput.setText(null);
        streetInput.setText(null);
        numberAddressInput.setText(null);
        neighborhoodInput.setText(null);
        cityInput.setText(null);
        stateInput.setText(null);
        countryCombo.setSelectedItem(null);
        areaCodeInput.setText(null);
        numberInput.setText(null);
        readerTypeCombo.setSelectedItem(null);
        readerList.clearSelection();
    }

    private void updateList() {
        try {
            List<Reader> readers = _readerDao.getAll();
            readerList.setListData(readers.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateContryCombo() {
        countryCombo.setModel(new DefaultComboBoxModel<>(Country.values()));
    }

    private void updateReaderTypeCombo() {
        readerTypeCombo.setModel(new DefaultComboBoxModel<>(ReaderType.values()));
    }

    private boolean hasEmptyFields() {
        ReaderType readerType = (ReaderType) readerTypeCombo.getSelectedItem();

        return nameInput.getText().isEmpty() ||
                streetInput.getText().isEmpty() ||
                numberInput.getText().isEmpty() ||
                neighborhoodInput.getText().isEmpty() ||
                cityInput.getText().isEmpty() ||
                stateInput.getText().isEmpty() ||
                countryCombo.getSelectedItem().toString().isEmpty() ||
                areaCodeInput.getText().isEmpty() ||
                readerTypeCombo.getSelectedItem().toString().isEmpty() ||
                (readerType == STUDENT ? registerInput.getText().isEmpty()
                        : (readerType == TEACHER ? subjectInput.getText().isEmpty() : false));

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
            Reader newReader;

            Country country = (Country) countryCombo.getSelectedItem();
            ReaderType readerType = (ReaderType) readerTypeCombo.getSelectedItem();

            Address newAddress = new Address(
                    streetInput.getText(),
                    Integer.parseInt(numberAddressInput.getText()),
                    neighborhoodInput.getText(),
                    cityInput.getText(),
                    stateInput.getText(),
                    country
            );

            Telephone newTelephone = new Telephone(
                    Integer.parseInt(areaCodeInput.getText()),
                    Long.parseLong(numberInput.getText())
            );

            switch (readerType) {
                case STUDENT:
                    newReader  = new Student(
                            _readerDao.getNextCode(),
                            nameInput.getText(),
                            newAddress,
                            newTelephone,
                            readerType,
                            registerInput.getText()
                    );
                    break;

                case TEACHER:
                    newReader = new Teacher(
                            _readerDao.getNextCode(),
                            nameInput.getText(),
                            newAddress,
                            newTelephone,
                            readerType,
                            subjectInput.getText()
                    );
                    break;

                default:
                    newReader = new Student();
                    break;
            }

            _readerDao.save(newReader);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void change() {
        try {
            Nationality newNationality = (Nationality) countryCombo.getSelectedItem();

            /*Author updatedAuthor = new Author(
                    Long.parseLong(codeInput.getText()),
                    nameInput.getText(),
                    streetInput.getText(),
                    newNationality.name()
            );

            _readerDao.update(updatedAuthor);*/

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void switchFieldByReaderType(ReaderType selectedReaderType) {

        switch (selectedReaderType) {
            case STUDENT:
                registerInput.setVisible(true);
                registerLabel.setVisible(true);
                subjectInput.setVisible(false);
                subjectLabel.setVisible(false);
                break;

            case TEACHER:
                registerInput.setVisible(false);
                registerLabel.setVisible(false);
                subjectInput.setVisible(true);
                subjectLabel.setVisible(true);
                break;

            default:
                registerInput.setVisible(false);
                registerLabel.setVisible(false);
                subjectInput.setVisible(false);
                subjectLabel.setVisible(false);
                break;
        }
    }

}
