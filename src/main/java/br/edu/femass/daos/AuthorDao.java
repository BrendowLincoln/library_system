package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.utils.GlobalConstants;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao extends Persistence implements Dao<Author> {
    private final String FILE_NAME = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "authors.json";

    @Override
    public void save(Author author) throws Exception {
        List<Author> authors = getAll();
        authors.add(author);
        String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(authors);

        FileOutputStream out = new FileOutputStream(FILE_NAME);
        out.write(json.getBytes());
        out.close();
    }

    @Override
    public List<Author> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(FILE_NAME);
            String json = new String(in.readAllBytes());
            in.close();
            List<Author> authors = getObjectMapper().readValue(json, new TypeReference<List<Author>>(){});
            return authors;
        } catch (FileNotFoundException f) {
            return new ArrayList<Author>();
        }
    }

    @Override
    public void update(Author author) throws Exception {

    }

    @Override
    public void delete() throws Exception {

    }
}
