package br.edu.femass.models;

public class Author {
    private Integer code;
    private String name;
    private String secondName;
    private String nationality;

    private static Integer _initialCode = 1;

    public Author() { }

    public Author(
        String name,
        String secondName,
        String nationality
    ) {
        this.code = _initialCode;
        this.name = name;
        this.secondName = secondName;
        this.nationality = nationality;

        _initialCode++;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String getNationality() {
        return this.nationality;
    }

    @Override
    public String toString() {
        return this.name + " " + this.secondName;
    }

    @Override
    public boolean equals(Object object) {
        Author author = (Author) object;
        return this.code.equals(author.getCode().toString());
    }
}
