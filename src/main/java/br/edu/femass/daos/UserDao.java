package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.models.User;
import br.edu.femass.utils.GlobalConstants;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Persistence implements Dao<User> {
    private String _fileName;

    public UserDao() {
        _fileName = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "users.json";
    }

    public UserDao(String pathToStorage) {
        _fileName = pathToStorage;
    }

    @Override
    public void save(User user) throws Exception {

    }

    @Override
    public List<User> getAll() throws Exception {
        try {
            FileInputStream in = new FileInputStream(_fileName);
            String json = new String(in.readAllBytes());
            in.close();
            List<User> users = getObjectMapper().readValue(json, new TypeReference<List<User>>(){});
            return users;

        } catch (FileNotFoundException f) {
            return new ArrayList<User>();
        }
    }

    @Override
    public User getByCode(Long code) throws Exception {
        return null;
    }

    @Override
    public Long getNextCode() throws Exception {
        return null;
    }

    @Override
    public void update(User user) throws Exception {

    }

    @Override
    public void delete(Long code) throws Exception {

    }

    public User getByUseranmeAndPassword(String username, String password) {
        try {
            List<User> users = getAll();

            return users.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
}
