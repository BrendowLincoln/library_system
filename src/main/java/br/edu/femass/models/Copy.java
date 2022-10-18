package br.edu.femass.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Copy {
    private Long code;
    private LocalDate acquisitionDate;
    private Long bookCode;
    private Boolean isFixed;
    private Boolean loaned;

    public Copy() {
        this.loaned = false;
    }

    public Copy(Long code, LocalDate acquisitionDate, Boolean isFixed) {
        this.code = code;
        this.acquisitionDate = acquisitionDate;
        this.isFixed = isFixed;
        this.loaned = false;
    }

    public Long getCode() {
        return this.code;
    }

    public LocalDate getAcquisitionDate() {
        return this.acquisitionDate;
    }

    public Boolean getLoaned() { return this.loaned; }

    public Boolean getIsFixed() { return this.isFixed; }

    public void setLoaned(Boolean loaned) {
        this.loaned = loaned;
    }

    public Long getBookCode() {
        return bookCode;
    }

    public void setBookCode(Long bookCode) {
        this.bookCode = bookCode;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        return "Código: " + this.code + " - " + (this.isFixed ? "Fixo" : "Levar") + " - " + (this.loaned ? "Emprestado" : "Disponível");
    }

    @Override
    public boolean equals(Object object) {
        Copy copy = (Copy) object;
        if(copy == null) {
            return false;
        }
        return this.code.equals(copy.code);
    }


}
