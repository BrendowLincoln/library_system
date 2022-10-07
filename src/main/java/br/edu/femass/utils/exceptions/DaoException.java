package br.edu.femass.utils.exceptions;

public class DaoException extends Exception {
    public DaoException(String errorMessage)  {
        super(errorMessage);
    }
}