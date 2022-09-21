package br.edu.femass.daos;

import java.util.*;

public interface Dao<T> {
    public void save(T object) throws Exception;
    public List<T> getAll() throws Exception;
    public void update(T object) throws Exception;
    public void delete() throws Exception;
}