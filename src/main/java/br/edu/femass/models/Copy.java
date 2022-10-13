package br.edu.femass.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Copy {
    private Long code;
    private LocalDate acquisitionDate;
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

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        return "Copy: " + this.code + "- Emprestado: " + (this.loaned ? "Emprestado" : "Disponível");
    }

    @Override
    public boolean equals(Object object) {
        Copy copy = (Copy) object;
        return this.code.equals(copy.code);
    }
}
