package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.Nationality;
import br.edu.femass.utils.exceptions.AuthorDaoExceptionMessage;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;

public class AuthorDaoTest {
    private AuthorDao _sut;
    private final String FILE_TEST_PATH = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "author_test.json";

    @BeforeEach
    public void setUpTest() throws IOException {
        _sut = new AuthorDao(FILE_TEST_PATH);

        var fileWrite = new FileWriter(FILE_TEST_PATH);
        fileWrite.write("[]");
        fileWrite.close();
    }

    @Test
    @DisplayName("Deve salvar um objeto de Autor no arquivo json")
    public void should_save_an_author_in_json_file() throws Exception {
        //Given
        Author newAuthor = new Author(
                1L,
                "Teste",
                "Autor",
                Nationality.BRASILEIRO.name()
        );

        //When
        _sut.save(newAuthor);

        //Then
        Author result =  _sut.getByCode(newAuthor.getCode());
        Assertions.assertEquals(newAuthor.getCode(), result.getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o autor tiver os valores vazios e não conseguir salvar")
    public void should_throw_an_exception_when_author_has_values_empty_and_can_not_save() {
        //Given
        Author author = new Author();

        //When //Then
         Assertions.assertThrows(IllegalArgumentException.class, () -> _sut.save(author), AuthorDaoExceptionMessage.COULD_NOT_SAVE_AUTHOR);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o autor tiver os valores")
    public void should_not_throw_an_exception_when_author_has_values() {
        //Given
        Author author = new Author(
                1L,
                "Teste",
                "Autor",
                Nationality.BRASILEIRO.name()
        );

        //When // Then
        Assertions.assertDoesNotThrow(() -> _sut.save(author), AuthorDaoExceptionMessage.COULD_NOT_SAVE_AUTHOR);
    }
}
