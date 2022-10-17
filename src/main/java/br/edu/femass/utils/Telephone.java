package br.edu.femass.utils;

public class Telephone {
    private Integer areaCode;
    private Long number;

    public Telephone() {

    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public Long getNumber() {
        return number;
    }

    public Telephone(Integer areaCode, Long number) {
        this.areaCode = areaCode;
        this.number = number;
    }
}
