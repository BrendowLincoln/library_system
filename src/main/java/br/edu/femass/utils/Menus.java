package br.edu.femass.utils;

public enum Menus {
    BOOKS("Books"),
    AUTHORS("Authors"),
    READERS("Readers"),
    LOANS("Loans"),
    EMPLOYEES("Employees");

    public final String value;

    Menus(String option) {
        value = option;
    }

    public String getValue() {
        return value;
    }
}
