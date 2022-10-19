package br.edu.femass.models;

import br.edu.femass.utils.Address;
import br.edu.femass.utils.Country;
import br.edu.femass.utils.ReaderType;
import br.edu.femass.utils.Telephone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanTest {

    @Test
    @DisplayName("Deve retornar o texto do emréstimo com situção de Emprestado")
    public void should_return_loan_string_with_loaned_status() {
        //Given
        Copy copy = new Copy(3L, LocalDate.parse("2020-04-10"),  false);
        List<Copy> copies = new ArrayList<Copy>();
        copies.add(copy);
        Reader reader = new Reader(
                2L,
                "Fulano",
                new Address(
                        "fasdf",
                        32,
                        "asdfa",
                        "fasdfa",
                        "fasdfas",
                        Country.ABW),
                new Telephone(22, 2325423L),
                ReaderType.STUDENT,
                15
        );

        Loan loan = new Loan(4L, copies, reader, LocalDate.now());

        String expectedResult = "Empréstimo 4: Fulano - Emprestado";

        //When
        String result = loan.toString();

        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Deve retornar o texto do emréstimo com situção de Devolvido")
    public void should_return_loan_string_with_returned_status() {
        //Given
        Copy copy = new Copy(3L, LocalDate.parse("2020-04-10"),  false);
        List<Copy> copies = new ArrayList<Copy>();
        copies.add(copy);
        Reader reader = new Reader(
                2L,
                "Fulano",
                new Address(
                        "fasdf",
                        32,
                        "asdfa",
                        "fasdfa",
                        "fasdfas",
                        Country.ABW),
                new Telephone(22, 2325423L),
                ReaderType.STUDENT,
                15
        );

        Loan loan = new Loan(4L, copies, reader, LocalDate.parse("2022-09-25"));
        loan.setReturnDate(LocalDate.parse("2022-10-10"));

        String expectedResult = "Empréstimo 4: Fulano - Devolvido";

        //When
        String result = loan.toString();

        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Deve retornar o texto do emréstimo com situção de Atrasado")
    public void should_return_loan_string_with_overdue_status() {
        //Given
        Copy copy = new Copy(3L, LocalDate.parse("2020-04-10"),  false);
        List<Copy> copies = new ArrayList<Copy>();
        copies.add(copy);
        Reader reader = new Reader(
                2L,
                "Fulano",
                new Address(
                        "fasdf",
                        32,
                        "asdfa",
                        "fasdfa",
                        "fasdfas",
                        Country.ABW),
                new Telephone(22, 2325423L),
                ReaderType.STUDENT,
                15
        );

        Loan loan = new Loan(4L, copies, reader, LocalDate.parse("2022-09-25"));

        String expectedResult = "Empréstimo 4: Fulano - Atrasado";

        //When
        String result = loan.toString();

        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Deve retornar se o empréstimo está em atraso")
    public void  should_return_if_loan_is_overdue() {
        //Given
        Copy copy = new Copy(3L, LocalDate.parse("2020-04-10"),  false);
        List<Copy> copies = new ArrayList<Copy>();
        copies.add(copy);
        Reader reader = new Reader(
                2L,
                "Fulano",
                new Address(
                        "fasdf",
                        32,
                        "asdfa",
                        "fasdfa",
                        "fasdfas",
                        Country.ABW),
                new Telephone(22, 2325423L),
                ReaderType.STUDENT,
                15
        );

        Loan loan = new Loan(4L, copies, reader, LocalDate.parse("2022-09-25"));

        //When
        Boolean result = loan.isOverdue();

        //Then
        Assertions.assertTrue(result);
    }
}
