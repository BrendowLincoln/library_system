package br.edu.femass.gui;

import br.edu.femass.daos.CopyDao;
import br.edu.femass.models.Copy;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CopyGui {
    private JPanel copiesPanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList copyList;
    private JPanel formContainer;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JFormattedTextField acquisitionDateInput;
    private JCheckBox isFixCheck;

    private boolean _isNew;
    private CopyDao _copyDao;
    private List<Copy> currentCopies;

    public CopyGui(List<Copy> copies) {
        this.currentCopies = copies;
        _isNew = false;
        _copyDao = new CopyDao();

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
                    copies.removeIf(copy -> copy.getCode().equals(Long.parseLong(codeInput.getText())));
                    clearFields();
                    setEditMode(false);
                    updateList();
                    copyList.clearSelection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEditMode(false);
                clearFields();
                copyList.clearSelection();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasEmptyFields()) {
                    JOptionPane.showMessageDialog(null, GlobalExceptionMessage.FILLING_IN_THE_FIELDS_IS_MANDATORY);
                    return;
                }

                if(!isAcquisitionDateValid(acquisitionDateInput.getText())) {
                    JOptionPane.showMessageDialog(null, GlobalExceptionMessage.INVALID_DATE);
                    return;
                }
                save();
                copyList.clearSelection();
            }
        });
        copyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                Copy selectedCopy = (Copy) copyList.getSelectedValue();
                if(selectedCopy == null) return;

                codeInput.setText(selectedCopy.getCode().toString());
                acquisitionDateInput.setText(selectedCopy.getAcquisitionDate().format(formatter));
                isFixCheck.setSelected(selectedCopy.getIsFixed());
                setEditMode(true);
            }
        });
    }

    //
    public JPanel getCopiesPanel() {
        return copiesPanel;
    }

    public List<Copy> getCopies() {
        return this.currentCopies;
    }


    // Private methods
    private void initialize() {
        setEditMode(false);
        updateList();
        setAcquisitionDateMask();
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
            Copy newCopy = new Copy(
                    _copyDao.getNextCode(),
                    convertStringDateToLocalDate(acquisitionDateInput.getText()),
                    isFixCheck.isSelected()
            );
            currentCopies.add(newCopy);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void change() {
        try {
            Copy updatedCopy = new Copy(
                    Long.parseLong(codeInput.getText()),
                    convertStringDateToLocalDate(acquisitionDateInput.getText()),
                    isFixCheck.isSelected()
            );
            for (int i = 0; i < currentCopies.size(); i++) {
                if (currentCopies.get(i).getCode() == updatedCopy.getCode()) {
                    currentCopies.set(i, updatedCopy);
                }
            }

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);

        if(editing) {
            acquisitionDateInput.setEditable(true);
            isFixCheck.setEnabled(true);

            addButton.setVisible(false);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);


            if(!_isNew) {
                deleteButton.setVisible(true);
            }
        }
        else {
            acquisitionDateInput.setEditable(false);
            isFixCheck.setEnabled(false);

            addButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void clearFields() {
        codeInput.setText(null);
        acquisitionDateInput.setText(null);
        isFixCheck.setSelected(false);
    }

    private void updateList() {
        try {
            List<Copy> copiesList = this.currentCopies;
            copyList.setListData(copiesList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasEmptyFields() {
        return acquisitionDateInput.getText().isEmpty();
    }

    private boolean isAcquisitionDateValid(String date) {
        try {
            LocalDate parsedDate = convertStringDateToLocalDate(date);
            return parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString().equals(date) && (parsedDate.isBefore(LocalDate.now()) || parsedDate.equals(LocalDate.now()) );
        } catch (Exception e) {
            return false;
        }
    }


    private void setAcquisitionDateMask() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(acquisitionDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private LocalDate convertStringDateToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parserDate = LocalDate.parse(date, formatter);
        return parserDate;
    }
}
