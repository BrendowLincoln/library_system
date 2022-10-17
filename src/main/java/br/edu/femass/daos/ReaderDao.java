package br.edu.femass.daos;

import br.edu.femass.models.Reader;
import br.edu.femass.models.Student;
import br.edu.femass.models.Teacher;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReaderDao extends Persistence implements Dao<Reader> {
    private String _fileName;

    public ReaderDao() {
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "readers.json";
    }

    public ReaderDao(String pathToStorage) {
        _fileName = pathToStorage;
    }

    @Override
    public void save(Reader reader) throws Exception {
        try {
            if(hasEmptyProperties(reader)) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
            }

            List<Reader> readers = getAll();
            readers.add(reader);
            writeInFile(readers);
        } catch (Exception de) {
            throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
        }
    }

    @Override
    public List<Reader> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            ObjectMapper mapper = getObjectMapper();
            mapper.registerSubtypes(new NamedType(Student.class, "student"));
            mapper.registerSubtypes(new NamedType(Teacher.class, "teacher"));
            List<Reader> readers = mapper.readValue(json, new TypeReference<List<Reader>>(){});
            return readers;

        } catch (FileNotFoundException f) {
            return new ArrayList<Reader>();
        }
    }

    @Override
    public Reader getByCode(Long code) throws Exception {
        return null;
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("reader");
    }

    @Override
    public void update(Reader reader) throws Exception {

    }

    @Override
    public void delete(Long code) throws Exception {

    }

    private void writeInFile(List<Reader> authors) throws Exception  {
        ObjectMapper mapper = getObjectMapper();
        mapper.registerSubtypes(new NamedType(Student.class, "student"));
        mapper.registerSubtypes(new NamedType(Teacher.class, "teacher"));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authors);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }

    private boolean hasEmptyProperties(Reader reader) {
        return  reader.getCode() == null ||
                reader.getName().isEmpty() ||
                reader.getAddress() == null ||
                reader.getTelephone() == null ||
                reader.getReaderType() == null ||
                reader.getMaximumReturnPeriod() == 0;
    }
}
