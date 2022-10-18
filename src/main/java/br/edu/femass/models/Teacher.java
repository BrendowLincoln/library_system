package br.edu.femass.models;

import br.edu.femass.utils.Address;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.ReaderType;
import br.edu.femass.utils.Telephone;
import com.fasterxml.jackson.annotation.JsonTypeName;

public class Teacher extends Reader {
    private String subject;

    public Teacher() { }

    public Teacher(
            Long code,
            String name,
            Address address,
            Telephone telephone,
            ReaderType readerType,
            String subject
    ) {
        super(code, name, address, telephone, readerType, GlobalConstants.LOAN_DAYS_ALLOWED_FOR_TEACHER);
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }
}
