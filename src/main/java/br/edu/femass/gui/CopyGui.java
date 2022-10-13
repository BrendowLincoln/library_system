package br.edu.femass.gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

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

    public CopyGui() {
        MaskFormatter dateMask = null;
        try {
            dateMask = new MaskFormatter("##/##/####");
            dateMask.install(acquisitionDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public JPanel getCopiesPanel() {
        return copiesPanel;
    }
}
