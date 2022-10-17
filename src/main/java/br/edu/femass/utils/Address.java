package br.edu.femass.utils;

public class Address {
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String state;
    private Country country;

    public Address(String street, Integer number, String neighborhood, String city, String state, Country country) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() { return city; }

    public String getState() {
        return state;
    }

    public Country getCountry() {
        return country;
    }
}
