package br.edu.femass.utils;

public enum ReaderType {
    EMPTY(""),
    STUDENT("Aluno"),
    TEACHER("Professor");

    private String name;

    ReaderType(String name) {
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
