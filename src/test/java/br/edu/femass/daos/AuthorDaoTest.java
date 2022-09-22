package br.edu.femass.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthorDaoTest {
    private AuthorDao _sut;

    @BeforeEach
    public void setUpTest() {
        _sut = new AuthorDao();
    }

    @Test
    @DisplayName("Deve salvar um objeto de Autor no arquivo json")
    public void should_save_an_author_in_json_file() {
        //Given


        //When

        //Then
    }
}
