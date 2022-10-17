package br.edu.femass.models;

import br.edu.femass.utils.Address;
import br.edu.femass.utils.GlobalConstants;
import br.edu.femass.utils.ReaderType;
import br.edu.femass.utils.Telephone;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("student")
public class Student extends Reader {
    private String register;

    public Student() { }

    public Student(
            Long code,
            String name,
            Address address,
            Telephone telephone,
            ReaderType readerType,
            String register
    ) {
        super(code, name, address, telephone, readerType, GlobalConstants.LOAN_DAYS_ALLOWED_FOR_STUDENT);
        this.register = register;
    }

    public String getRegister() {
        return this.register;
    }
}
