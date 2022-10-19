package br.edu.femass.gui;

import br.edu.femass.daos.*;
import br.edu.femass.models.*;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoanGui {
    private JPanel loanPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList loanList;
    private JPanel formContainer;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton devolutionButton;
    private JButton addCopyButton;
    private JFormattedTextField loanDateInput;
    private JFormattedTextField expectedReturnDateInput;
    private JFormattedTextField returnDateInput;
    private JComboBox readerCombo;
    private JComboBox copiesCombo;
    private JButton overdueButton;

    private StudentDao _studentDao;
    private TeacherDao _teacherDao;
    private LoanDao _loanDao;
    private Boolean _isNew = true;
    private List<Copy> _currentCopies = new ArrayList<Copy>();

    public LoanGui() {
        _loanDao = new LoanDao();
        _studentDao = new StudentDao();
        _teacherDao = new TeacherDao();


        initialize();

        addCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSelectCopiesFormDialog();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _isNew = true;
                _currentCopies = new ArrayList<Copy>();
                setEditMode(true);
                updateReaders();

                setEditMode(true);
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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                setEditMode(false);
            }
        });

        devolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loan selectedLoan = (Loan) loanList.getSelectedValue();
                _loanDao.doReturn(selectedLoan.getCode());

                clearFields();
                setEditMode(false);
                updateList();
            }
        });

        loanList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _isNew = false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                updateReaders();

                Loan selectedLoan = (Loan) loanList.getSelectedValue();
                if(selectedLoan == null) return;

                codeInput.setText(selectedLoan.getCode().toString());
                loanDateInput.setText(selectedLoan.getLoanDate().format(formatter));
                readerCombo.setSelectedItem(selectedLoan.getReader());
                expectedReturnDateInput.setText(selectedLoan.getExpectedReturnDate().format(formatter));
                updateCopiesCombo(selectedLoan.getCopies());

                if(selectedLoan.getReturnDate() != null) {
                    returnDateInput.setText(selectedLoan.getReturnDate().format(formatter));
                }


                setEditMode(true);
            }
        });

        overdueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOverdueLoansDialog();
            }
        });
    }

    public JPanel getLoanPanel() {
        return this.loanPanel;
    }


    //private methods
    private void initialize() {
        setEditMode(false);
        updateReaders();
        updateList();
        setAcquisitionDateMask(loanDateInput);
        setAcquisitionDateMask(expectedReturnDateInput);
        setAcquisitionDateMask(returnDateInput);
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

    private void setEditMode(boolean editing) {
        codeInput.setEditable(false);
        expectedReturnDateInput.setEditable(false);
        returnDateInput.setEditable(false);

        if(editing) {
            copiesCombo.setEnabled(true);
            readerCombo.setEnabled(true);
            loanDateInput.setEditable(true);

            addButton.setVisible(false);
            overdueButton.setVisible(false);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addCopyButton.setVisible(true);


            if(!_isNew) {
                devolutionButton.setVisible(true);
                saveButton.setVisible(false);
                addCopyButton.setVisible(false);
            }

            Loan selectedLoan = (Loan) loanList.getSelectedValue();

            if(selectedLoan != null && selectedLoan.getReturnDate() != null) {
                devolutionButton.setVisible(false);
            }


        }
        else {
            copiesCombo.setEnabled(false);
            readerCombo.setEnabled(false);
            loanDateInput.setEditable(false);

            addButton.setVisible(true);
            overdueButton.setVisible(true);
            cancelButton.setVisible(false);
            saveButton.setVisible(false);
            devolutionButton.setVisible(false);
            addCopyButton.setVisible(false);
        }
    }

    private void save() {
        if(!isDateValid(loanDateInput.getText())) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.INVALID_DATE);
            return;
        }

        create();
    }

    private void clearFields() {
        codeInput.setText(null);
        copiesCombo.removeAllItems();
        readerCombo.removeAllItems();
        loanDateInput.setText(null);
        expectedReturnDateInput.setText(null);
        returnDateInput.setText(null);
        loanList.clearSelection();
    }

    private void updateList() {
        try {
            List<Loan> loans = _loanDao.getAll();
            loanList.setListData(loans.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateReaders() {
        readerCombo.removeAllItems();
        try {
            List<Student> students = _studentDao.getAll();
            List<Teacher> teachers = _teacherDao.getAll();

            List<Reader> readers = new ArrayList<Reader>();
            readers.addAll(students);
            readers.addAll(teachers);
            readers.sort(Comparator.comparing(Reader::getCode));

            for(Reader reader: readers) {
                readerCombo.addItem(reader);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, GlobalExceptionMessage.COULD_NOT_LOAD_READERS);
        }
    }

    private void create() {
        try {
            Reader readerSelected = (Reader) readerCombo.getSelectedItem();

            Loan newLoan = new Loan(
                    _loanDao.getNextCode(),
                    _currentCopies,
                    readerSelected,
                    convertStringDateToLocalDate(loanDateInput.getText())
            );
            _loanDao.save(newLoan);

            clearFields();
            setEditMode(false);
            updateList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private boolean hasEmptyFields() {
        return copiesCombo.getItemCount() <= 0 ||
                (readerCombo.getSelectedItem() == null || readerCombo.getSelectedItem().toString().isEmpty()) ||
                copiesCombo.getItemCount() <= 0;
    }

    private void openSelectCopiesFormDialog() {

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.57);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.4);

        JDialog frame = new JDialog(new Frame(), true);
        SelectCopiesGui selectCopiesGui = new SelectCopiesGui(_currentCopies);
        frame.setContentPane(selectCopiesGui.getSelectCopiesPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setTitle("Selecionar exemplares");
        frame.setLocation(
                screenSize.width/2- frame.getSize().width/2,
                screenSize.height/2-frame.getSize().height/2

        );

        frame.setVisible(true);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                _currentCopies = selectCopiesGui.getCopies();
                updateCopiesCombo(_currentCopies);
            }

        });
    }


    private void openOverdueLoansDialog() {

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Integer screenWidth = (int) (screenSize.getWidth() * 0.3);
        final Integer screenHeight = (int) (screenSize.getHeight() * 0.6);

        JDialog frame = new JDialog(new Frame(), true);
        OverdueLoansGui selectCopiesGui = new OverdueLoansGui();
        frame.setContentPane(selectCopiesGui.getOverdueLoansPanel());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setTitle("Empréstimpos em atraso");
        frame.setLocation(
                screenSize.width/2- frame.getSize().width/2,
                screenSize.height/2-frame.getSize().height/2

        );

        frame.setVisible(true);
        frame.pack();
    }

    private boolean isDateValid(String date) {
        try {
            LocalDate parsedDate = convertStringDateToLocalDate(date);
            return parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString().equals(date) && (parsedDate.isBefore(LocalDate.now()) || parsedDate.equals(LocalDate.now()) );
        } catch (Exception e) {
            return false;
        }
    }


    private void setAcquisitionDateMask(JFormattedTextField input) {
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(input);
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
