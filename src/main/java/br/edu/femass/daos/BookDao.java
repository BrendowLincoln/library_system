package br.edu.femass.daos;

import br.edu.femass.models.Book;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BookDao extends Persistence implements Dao<Book> {
    private String _fileName;

    public BookDao() { _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "books.json";  }

    public BookDao(String pathStorage) { _fileName = pathStorage; }

    @Override
    public void save(Book book) throws Exception {
        try {
            if(book.getCode() == null || book.getTitle().isEmpty() || book.getCopies().size() == 0 ) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_BOOK);
            }

            List<Book> books = getAll();
            books.add(book);
            writeInFile(books);
        } catch (Exception de) {
            throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_BOOK);
        }
    }

    @Override
    public List<Book> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            List<Book> books = getObjectMapper().readValue(json, new TypeReference<List<Book>>(){});
            return books;

        } catch (FileNotFoundException f) {
            return new ArrayList<Book>();
        }
    }

    @Override
    public Book getByCode(Long code) throws Exception {
        try {
            List<Book> books = getAll();
            if (books == null || books.isEmpty()) {
                return null;
            }

            return books.stream().filter((book) -> book.getCode().equals(code)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("book");
    }

    @Override
    public void update(Book book) throws Exception {
        List<Book> books = getAll();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCode() == book.getCode()) {
                books.set(i, book);
            }
        }

        writeInFile(books);
    }

    @Override
    public void delete(Long code) throws Exception {
        List<Book> authors = getAll();
        authors.removeIf((author) -> author.getCode() == code);
        writeInFile(authors);
    }

    private void writeInFile(List<Book> books) throws Exception  {
        String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(books);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }
}
