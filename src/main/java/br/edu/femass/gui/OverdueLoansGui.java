package br.edu.femass.gui;

import br.edu.femass.daos.LoanDao;
import br.edu.femass.models.Loan;

import javax.swing.*;
import java.util.List;

public class OverdueLoansGui {
    private JPanel overdueLoansPanel;
    private JScrollPane listContainer;
    private JList overdueLoansList;

    private LoanDao _loanDao;

    public OverdueLoansGui() {
        _loanDao = new LoanDao();
        updateList();
    }

    public JPanel getOverdueLoansPanel() {
        return overdueLoansPanel;
    }

    private void updateList() {
        try {
            List<Loan> loans = _loanDao.getOverdueLoans();
            overdueLoansList.setListData(loans.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
