package br.edu.femass.daos;

import java.util.List;

public interface Dao<T> {
    void save(T t) throws Exception;
    List<T> getAll() throws Exception;
    void update(T t) throws Exception;
    void delete() throws Exception;
}
