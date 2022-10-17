package br.edu.femass.models;

import br.edu.femass.utils.Nationality;

public class Author {
    private Long code;
    private String name;
    private String secondName;
    private Nationality nationality;

    public Author() { }

    public Author(
        Long code,
        String name,
        String secondName,
        Nationality nationality
    ) {
        this.code = code;
        this.name = name;
        this.secondName = secondName;
        this.nationality = nationality;
    }

    public Long getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    @Override
    public String toString() {
        return this.name + " " + this.secondName;
    }

    @Override
    public boolean equals(Object object) {
        Author author = (Author) object;
        if(author == null) {
            return false;
        }
        return this.code.equals(author.getCode());
    }
}
