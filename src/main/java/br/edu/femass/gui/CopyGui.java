package br.edu.femass.gui;

import javax.swing.*;

public class CopyGui {
    private JPanel copiesPanel;
    private JPanel mainPanel;
    private JScrollPane listContainer;
    private JList authorList;
    private JPanel formContainer;
    private JTextField firstNameInput;
    private JTextField secondNameInput;
    private JTextField codeInput;
    private JButton addButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox nationalityCombo;

    public CopyGui() { }

    public JPanel getCopiesPanel() {
        return copiesPanel;
    }
}
