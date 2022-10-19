package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.models.Book;
import br.edu.femass.models.Loan;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class LoanDao extends Persistence implements Dao<Loan> {
    private String _fileName;
    private BookDao _bookDao;

    public LoanDao() {
        _bookDao = new BookDao();
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "loans.json";
    }

    public LoanDao(String pathStorage) {
        _bookDao = new BookDao();
        _fileName = pathStorage;
    }

    @Override
    public void save(Loan loan) throws Exception {
        try {
            if(hasEmptyFields(loan)) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_BOOK);
            }

            loan.getCopies().forEach(copy -> {
                try {
                    Book book = _bookDao.getByCode(copy.getBookCode());
                    book.getCopies().forEach(x -> {
                       if(copy.getCode() == x.getCode()) {
                           x.setLoaned(true);
                       }
                    });
                    _bookDao.update(book);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                copy.setLoaned(true);
            });

            List<Loan> loans = getAll();
            loans.add(loan);
            writeInFile(loans);
        } catch (Exception de) {
            throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_LOAN);

        }
    }

    @Override
    public List<Loan> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            List<Loan> loans = getObjectMapper().readValue(json, new TypeReference<List<Loan>>(){});
            return loans;

        } catch (FileNotFoundException f) {
            return new ArrayList<Loan>();
        }
    }

    @Override
    public Loan getByCode(Long code) throws Exception {
        try {
            List<Loan> loans = getAll();
            if (loans == null || loans.isEmpty()) {
                return null;
            }

            return loans.stream().filter((loan) -> loan.getCode().equals(code)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("loans");
    }

    @Override
    public void update(Loan loan) throws Exception {
        List<Loan> loans = getAll();
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getCode() == loan.getCode()) {
                loans.set(i, loan);
            }
        }

        writeInFile(loans);
    }

    @Override
    public void delete(Long code) throws Exception {

    }

    public void doReturn(Long loanCode) {
        try {
            Loan loan = getByCode(loanCode);

            loan.setReturnDate(LocalDate.now());
            loan.getCopies().forEach(copy -> {
                try {
                    Book book = _bookDao.getByCode(copy.getBookCode());
                    book.getCopies().forEach(x -> {
                        if(copy.getCode() == x.getCode()) {
                            x.setLoaned(false);
                        }
                    });
                    _bookDao.update(book);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                copy.setLoaned(false);
            });

            update(loan);
        } catch (Exception de) {
            throw new IllegalArgumentException(de.getMessage());

        }
    }

    public List<Loan> getOverdueLoans() {
        try {
            List<Loan> loans = getAll();
            if (loans == null || loans.isEmpty()) {
                return new ArrayList<Loan>();
            }

            return loans.stream().filter((loan) -> loan.isOverdue()).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<Loan>();
        }
    }

    private boolean hasEmptyFields(Loan loan) {
        return loan.getCode() == null ||
                loan.getReader() == null ||
                loan.getCopies().size() == 0 ||
                loan.getLoanDate() == null ||
                loan.getExpectedReturnDate() == null;
    }

    private void writeInFile(List<Loan> loans) throws Exception  {
        String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(loans);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }
}
