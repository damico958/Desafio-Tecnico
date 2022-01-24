package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.TestConfig;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import com.damico958.desafio_tecnico.repository.SalesmanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SalesmanService.class, SalesmanRepository.class, CurrentFileDataService.class, CurrentFileDataRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class SalesmanServiceTest {
    @Autowired
    private SalesmanService salesmanService;

    private String cpf = "50090976053";
    private final String name = "Felipe";
    private final BigDecimal salary = new BigDecimal("1500.00");

    @Test
    public void shouldAcceptValidCpf() {
        assertTrue(salesmanService.save(cpf, name, salary));
    }

    @Test
    public void shouldNotAcceptWrongSizeCpf() {
        cpf = "5004312";
        String cpfValidatorExceptionMessage = "Fail! Could not accept this invalid CPF! Cause of exception from the validator API: [CPFError : INVALID DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> salesmanService.save(cpf, name, salary));
        assertEquals(cpfValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptWrongCheckDigitsCpf() {
        cpf = "50090976012";
        String cpfValidatorExceptionMessage = "Fail! Could not accept this invalid CPF! Cause of exception from the validator API: [CPFError : INVALID CHECK DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> salesmanService.save(cpf, name, salary));
        assertEquals(cpfValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptInvalidFormatCpf() {
        cpf = "500.909.760-53";
        String cpfValidatorExceptionMessage = "Fail! Could not accept this invalid CPF! Cause of exception from the validator API: [CPFError : INVALID FORMAT]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> salesmanService.save(cpf, name, salary));
        assertEquals(cpfValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptRepeatedDigitsCpf() {
        cpf = "11111111111";
        String cpfValidatorExceptionMessage = "Fail! Could not accept this invalid CPF! Cause of exception from the validator API: [CPFError : REPEATED DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> salesmanService.save(cpf, name, salary));
        assertEquals(cpfValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldFindSalesmanByName() {
        salesmanService.save(cpf, name, salary);
        Salesman salesman = salesmanService.findSalesmanByName("Felipe");
        assertEquals(cpf, salesman.getSalesmanCpf());
        assertEquals(name, salesman.getSalesmanName());
        assertEquals(salary, salesman.getSalesmanSalary());
    }

    @Test
    public void shouldNotFindSalesmanByName() {
        String salesmanNotFoundExceptionMessage = "Fail! Could not find a Salesman with this name!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> salesmanService.findSalesmanByName("Felipe"));
        assertEquals(salesmanNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldFindSalesmanByCpf() {
        salesmanService.save(cpf, name, salary);
        Salesman salesman = salesmanService.findSalesmanByCpf("50090976053");
        assertEquals(cpf, salesman.getSalesmanCpf());
        assertEquals(name, salesman.getSalesmanName());
        assertEquals(salary, salesman.getSalesmanSalary());
    }

    @Test
    public void shouldNotFindSalesmanByCpf() {
        String salesmanNotFoundExceptionMessage = "Fail! Could not find a Salesman with this CPF!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> salesmanService.findSalesmanByCpf("48397381091"));
        assertEquals(salesmanNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldRemoveSalesmanByCpf() {
        salesmanService.save(cpf, name, salary);
        Salesman salesman = salesmanService.findSalesmanByCpf("50090976053");
        assertEquals(cpf, salesman.getSalesmanCpf());
        assertEquals(name, salesman.getSalesmanName());
        assertEquals(salary, salesman.getSalesmanSalary());
        salesmanService.remove(cpf);
        String salesmanNotFoundExceptionMessage = "Fail! Could not find a Salesman with this CPF!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> salesmanService.findSalesmanByCpf("48397381091"));
        assertEquals(salesmanNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotRemoveSalesmanNotFoundByCpf() {
        String salesmanNotRemovedExceptionMessage = "Fail! Could not remove a Salesman with this CPF as it doesn't exist!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> salesmanService.remove("48397381091"));
        assertEquals(salesmanNotRemovedExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotSaveCpfAlreadyRegistered() {
        salesmanService.save(cpf, name, salary);
        String duplicateSalesmanExceptionMessage = "Fail! Could not create new Salesman as this CPF is already registered!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> salesmanService.save(cpf, name, salary));
        assertEquals(duplicateSalesmanExceptionMessage, exceptionOne.getMessage());
    }
}
