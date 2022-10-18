package br.edu.femass.models;

import java.time.LocalDate;
import java.util.List;

public class Loan {
    private Long code;
    private List<Copy> copies;
    private Reader reader;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;

    public Loan() { }

    public Loan(
            Long code,
            List<Copy> copies,
            Reader reader,
            LocalDate loanDate,
            LocalDate expectedReturnDate
    ) {
        this.code = code;
        this.copies = copies;
        this.reader = reader;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
    }


    public Long getCode() {
        return code;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public Reader getReader() {
        return reader;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
