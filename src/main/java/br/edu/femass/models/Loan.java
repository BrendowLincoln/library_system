package br.edu.femass.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
            LocalDate loanDate
    ) {
        this.code = code;
        this.copies = copies;
        this.reader = reader;
        this.loanDate = loanDate;
        this.expectedReturnDate = loanDate.plusDays(this.reader.getMaximumReturnPeriod());
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

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @JsonIgnore
    public boolean isOverdue() {
        return this.expectedReturnDate.isBefore(LocalDate.now()) && this.returnDate == null;
    }

    @Override
    public String toString() {
        return "Empréstimo " + this.code + ": " + this.getReader().getName() + " - " + (this.returnDate == null ? "Emprestado" : (!isOverdue() ? "Devolvido" : "Atrasado"));
    }
}
