package br.edu.femass.views;

import javax.swing.*;

public class BookGui {
    private JPanel bookPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList bookList;
    private JPanel formContainer;
    private JTextField firstNameInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox authorCombo;
    private JButton addCoopyButton;
    private JComboBox copyCombo;


    public JPanel getBookPanel() {
        return this.bookPanel;
    }
}
