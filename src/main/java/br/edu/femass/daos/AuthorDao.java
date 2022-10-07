package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.AuthorDaoExceptionMessage;
import br.edu.femass.utils.exceptions.DaoException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AuthorDao extends Persistence implements Dao<Author> {
    private String _fileName;


    public AuthorDao() {
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "authors.json";
    }

    public AuthorDao(String pathToStorage) {
        _fileName = pathToStorage;
    }

    @Override
    public void save(Author author) throws DaoException {
        try {

            if(author.getCode() == null || author.getName().isEmpty() || author.getSecondName().isEmpty() || author.getNationality().isEmpty()) {
                throw new DaoException(AuthorDaoExceptionMessage.COULD_NOT_SAVE_AUTHOR);
            }

            List<Author> authors = getAll();
            authors.add(author);
            writeInFile(authors);
        } catch (Exception de) {
            throw new DaoException(AuthorDaoExceptionMessage.COULD_NOT_SAVE_AUTHOR);
        }
    }

    @Override
    public List<Author> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            List<Author> authors = getObjectMapper().readValue(json, new TypeReference<List<Author>>(){});
            return authors;
        } catch (FileNotFoundException f) {
            return new ArrayList<Author>();
        }
    }

    @Override
    public Author getByCode(Long code) throws Exception {
        try {
            List<Author> authors = getAll();
            if (authors == null || authors.isEmpty()) {
                return null;
            }

            return authors.stream().filter((author) -> author.getCode().equals(code)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Long getNextCode() throws Exception {
          return nextEntityIndex("author");
    }

    @Override
    public void update(Author author) throws Exception {
        List<Author> authors = getAll();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getCode() == author.getCode()) {
                authors.set(i, author);
            }
        }

        writeInFile(authors);
    }

    @Override
    public void delete(Long code) throws Exception {
        List<Author> authors = getAll();
        authors.removeIf((author) -> author.getCode() == code);
        writeInFile(authors);
    }


    private void writeInFile(List<Author> authors) throws Exception  {
        String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(authors);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }
}
