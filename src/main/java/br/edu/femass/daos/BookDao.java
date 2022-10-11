package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.models.Book;
import br.edu.femass.utils.GlobalConstants;

import java.io.FileOutputStream;
import java.util.List;

public class BookDao extends Persistence implements Dao<Book> {
    private String _fileName;

    public BookDao() { _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "books.json";  }

    public BookDao(String pathStorage) { _fileName = pathStorage; }

    @Override
    public void save(Book book) throws Exception {

    }

    @Override
    public List<Book> getAll() throws Exception {
        return null;
    }

    @Override
    public Book getByCode(Long code) throws Exception {
        return null;
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("book");
    }

    @Override
    public void update(Book book) throws Exception {

    }

    @Override
    public void delete(Long code) throws Exception {

    }

    private void writeInFile(List<Book> books) throws Exception  {
        String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(books);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }
}
