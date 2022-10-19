package br.edu.femass.models;

import br.edu.femass.utils.Address;
import br.edu.femass.utils.ReaderType;
import br.edu.femass.utils.Telephone;

import java.util.Objects;


public class Reader {
    private Long code;
    private String name;
    private Address address;
    private Telephone telephone;
    private ReaderType readerType;
    private Integer maximumReturnPeriod;

    public Reader()  { }

    public Reader(
            Long code,
            String name,
            Address address,
            Telephone telephone,
            ReaderType readerType,
            Integer maximumReturnPeriod
    ) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.readerType = readerType;
        this.maximumReturnPeriod = maximumReturnPeriod;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public Integer getMaximumReturnPeriod() {
        return maximumReturnPeriod;
    }

    public ReaderType getReaderType() {
        return readerType;
    }

    @Override
    public String toString() {
        return this.name + " - " + readerType.getName();
    }

    @Override
    public boolean equals(Object o) {
        Reader reader = (Reader) o;
        if(reader == null) {
            return false;
        }

        return this.code.equals(reader.code);
    }


}
