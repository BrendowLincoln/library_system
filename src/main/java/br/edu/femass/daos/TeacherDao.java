package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.models.Teacher;
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

public class TeacherDao extends Persistence implements Dao<Teacher> {
    private String _fileName;

    public TeacherDao() {
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "teacher.json";
    }

    public TeacherDao(String pathToStorage) {
        _fileName = pathToStorage;
    }

    @Override
    public void save(Teacher teacher) throws Exception {
        try {
            if(hasEmptyProperties(teacher)) {
                throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
            }

            List<Teacher> teachers = getAll();
            teachers.add(teacher);
            writeInFile(teachers);
        } catch (Exception de) {
            throw new IllegalArgumentException(GlobalExceptionMessage.COULD_NOT_SAVE_READER);
        }
    }

    @Override
    public List<Teacher> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            ObjectMapper mapper = getObjectMapper();
            List<Teacher> teachers = mapper.readValue(json, new TypeReference<List<Teacher>>(){});
            return teachers;

        } catch (FileNotFoundException f) {
            return new ArrayList<Teacher>();
        }
    }

    @Override
    public Teacher getByCode(Long code) throws Exception {
        try {
            List<Teacher> teachers = getAll();
            if (teachers == null || teachers.isEmpty()) {
                return null;
            }

            return teachers.stream().filter((teacher) -> teacher.getCode().equals(code)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Long getNextCode() throws Exception {
        return nextEntityIndex("reader");
    }

    @Override
    public void update(Teacher teacher) throws Exception {
        List<Teacher> teachers = getAll();
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getCode() == teacher.getCode()) {
                teachers.set(i, teacher);
            }
        }

        writeInFile(teachers);
    }

    @Override
    public void delete(Long code) throws Exception {
        List<Teacher> teachers = getAll();
        teachers.removeIf((teacher) -> teacher.getCode() == code);
        writeInFile(teachers);
    }

    private void writeInFile(List<Teacher> teachers) throws Exception  {
        ObjectMapper mapper = getObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(teachers);
        FileOutputStream out = new FileOutputStream(_fileName);
        out.write(json.getBytes());
        out.close();
    }

    private boolean hasEmptyProperties(Teacher teacher) {
        return  teacher.getCode() == null ||
                teacher.getName().isEmpty() ||
                teacher.getAddress() == null ||
                teacher.getTelephone() == null ||
                teacher.getReaderType() == null ||
                teacher.getMaximumReturnPeriod() == 0 ||
                teacher.getSubject().isEmpty();
    }
}
