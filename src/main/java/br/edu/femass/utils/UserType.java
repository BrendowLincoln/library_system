package br.edu.femass.utils;

public enum UserType {
    ADMINISTRATOR("Administrador"),
    ATTENDANT("Atendente"),
    LIBRARIAN("Bibliotecário");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
