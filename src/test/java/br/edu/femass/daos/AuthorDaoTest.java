package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.Nationality;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                Nationality.BRASILEIRO
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
         Assertions.assertThrows(IllegalArgumentException.class, () -> _sut.save(author), GlobalExceptionMessage.COULD_NOT_SAVE_AUTHOR);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o autor tiver os valores")
    public void should_not_throw_an_exception_when_author_has_values() {
        //Given
        Author author = new Author(
                1L,
                "Teste",
                "Autor",
                Nationality.BRASILEIRO
        );

        //When // Then
        Assertions.assertDoesNotThrow(() -> _sut.save(author), GlobalExceptionMessage.COULD_NOT_SAVE_AUTHOR);
    }

    @Test
    @DisplayName("Deve recuperar todos os autores cadastrados")
    public void should_retriever_all_authors_registered() throws Exception {
        //Given
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < 5; i++) {
            Author newAuthor = new Author(
                    Long.valueOf(i),
                    "Fulano" + i,
                    " de Souza",
                    Nationality.JAMAICANO
            );
            _sut.save(newAuthor);
            authors.add(newAuthor);
        }

        //When
        List<Author> result = _sut.getAll();

        //Then
        Assertions.assertEquals(false, result.isEmpty());
        result.forEach(author -> {
            authors.contains(author);
        });
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia caso não encontre o arquivo")
    public void should_return_an_empty_list_if_file_not_found() throws Exception {
        //Given
        //When
        List<Author> result = _sut.getAll();

        //Then
        Assertions.assertEquals(true, result.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar o objeto autor pelo código")
    public void should_return_the_author_by_code() throws Exception  {
        //Given
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < 2; i++) {
            Author newAuthor = new Author(
                    Long.valueOf(i),
                    "Fulano" + i,
                    " de Souza",
                    Nationality.JAMAICANO
            );
            _sut.save(newAuthor);
            authors.add(newAuthor);
        }
        //When
        Author result = _sut.getByCode(authors.get(1).getCode());

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(authors.get(1).getCode(), result.getCode());
        Assertions.assertEquals(authors.get(1).getName(), result.getName());
        Assertions.assertEquals(authors.get(1).getSecondName(), result.getSecondName());
        Assertions.assertEquals(authors.get(1).getNationality(), result.getNationality());
    }


    @Test
    @DisplayName("Deve retornar o null caso não encontre o autor pelo código")
    public void should_return_null_in_case_did_not_find_the_author_by_code() throws Exception  {
        //Given
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < 2; i++) {
            Author newAuthor = new Author(
                    Long.valueOf(i),
                    "Fulano" + i,
                    " de Souza",
                    Nationality.JAMAICANO
            );
            _sut.save(newAuthor);
            authors.add(newAuthor);
        }
        //When
        Author result = _sut.getByCode(3L);

        //Then
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Deve atualizar o autor")
    public void should_update_author() throws Exception {
        //Given
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < 3; i++) {
            Author newAuthor = new Author(
                    Long.valueOf(i),
                    "Fulano" + i,
                    " de Souza",
                    Nationality.JAMAICANO
            );
            _sut.save(newAuthor);
            authors.add(newAuthor);
        }

        Author updatedAuthor = new Author(
                authors.get(1).getCode(),
                "Siclano",
                " Silva",
                Nationality.BRASILEIRO
        );

        //When
        _sut.update(updatedAuthor);
        Author result = _sut.getByCode(updatedAuthor.getCode());

        //Then
        Assertions.assertEquals(updatedAuthor.getCode(), result.getCode());
        Assertions.assertEquals(updatedAuthor.getName(), result.getName());
        Assertions.assertEquals(updatedAuthor.getSecondName(), result.getSecondName());
        Assertions.assertEquals(updatedAuthor.getNationality(), result.getNationality());
    }

    @Test
    @DisplayName("Deve deletar o autor")
    public void should_delete_author() throws Exception {
        //Given
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < 3; i++) {
            Author newAuthor = new Author(
                    Long.valueOf(i),
                    "Fulano" + i,
                    " de Souza",
                    Nationality.JAMAICANO
            );
            _sut.save(newAuthor);
            authors.add(newAuthor);
        }

        //When
        _sut.delete(authors.get(0).getCode());
        List<Author> results = _sut.getAll();

        //Then
        Assertions.assertFalse(results.stream().map(a -> a.getCode()).collect(Collectors.toList()).contains(authors.get(0).getCode()));
    }
}
