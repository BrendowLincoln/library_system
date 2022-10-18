package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.models.Reader;
import br.edu.femass.models.Student;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.exceptions.GlobalExceptionMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StudentDao extends Persistence implements Dao<Student> {
    private String _fileName;

    public StudentDao() {
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "student.json";
    }

    public StudentDao(String pathToStorage) {
        _fileName = pathToStorage;
    }

    @Override
    public void save(Student student) throws Exception {
        try {
            if(hasEmptyProperties(student)) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
            }

            List<Student> students = getAll();
            students.add(student);
            writeInFile(students);
        } catch (Exception de) {
            throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
        }
    }

    @Override
    public List<Student> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            ObjectMapper mapper = getObjectMapper();
            List<Student> students = mapper.readValue(json, new TypeReference<List<Student>>(){});
            return students;

        } catch (FileNotFoundException f) {
            return new ArrayList<Student>();
        }
    }

    @Override
    public Student getByCode(Long code) throws Exception {
        try {
            List<Student> students = getAll();
            if (students == null || students.isEmpty()) {
                return null;
            }

            return students.stream().filter((student) -> student.getCode().equals(code)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("reader");
    }

    @Override
    public void update(Student student) throws Exception {
        List<Student> students = getAll();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getCode() == student.getCode()) {
                students.set(i, student);
            }
        }

        writeInFile(students);
    }

    @Override
    public void delete(Long code) throws Exception {
        List<Student> students = getAll();
        students.removeIf((student) -> student.getCode() == code);
        writeInFile(students);
    }

    private void writeInFile(List<Student> students) throws Exception  {
        ObjectMapper mapper = getObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(students);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }

    private boolean hasEmptyProperties(Student student) {
        return  student.getCode() == null ||
                student.getName().isEmpty() ||
                student.getAddress() == null ||
                student.getTelephone() == null ||
                student.getReaderType() == null ||
                student.getMaximumReturnPeriod() == 0 ||
                student.getRegister().isEmpty();
    }
}
