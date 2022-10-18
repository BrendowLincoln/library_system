package br.edu.femass.daos;

import br.edu.femass.models.Book;
import br.edu.femass.models.Loan;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoanDao extends Persistence implements Dao<Loan> {
    private String _fileName;

    public LoanDao() { _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "loans.json";  }

    public LoanDao(String pathStorage) { _fileName = pathStorage; }

    @Override
    public void save(Loan loan) throws Exception {
        try {
            if(hasEmptyFields(loan)) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_BOOK);
            }

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
        return null;
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("loans");
    }

    @Override
    public void update(Loan loan) throws Exception {

    }

    @Override
    public void delete(Long code) throws Exception {

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
