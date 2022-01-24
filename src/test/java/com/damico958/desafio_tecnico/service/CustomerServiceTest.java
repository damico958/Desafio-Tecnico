package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.TestConfig;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Customer;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import com.damico958.desafio_tecnico.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CustomerService.class, CustomerRepository.class, CurrentFileDataService.class, CurrentFileDataRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    private String cnpj = "41789228000190";
    private final String name = "Felipe";
    private final String businessArea = "Desenvolvedor";

    @Test
    public void shouldAcceptValidCnpj() {
        assertTrue(customerService.save(cnpj, name, businessArea));
    }

    @Test
    public void shouldNotAcceptWrongSizeCnpj() {
        cnpj = "500312";
        String cnpjValidatorExceptionMessage = "Fail! Could not accept this invalid CNPJ! Cause of exception from the validator API: [CNPJError : INVALID DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> customerService.save(cnpj, name, businessArea));
        assertEquals(cnpjValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptWrongCheckDigitsCnpj() {
        cnpj = "41789228000111";
        String cnpjValidatorExceptionMessage = "Fail! Could not accept this invalid CNPJ! Cause of exception from the validator API: [CNPJError : INVALID CHECK DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> customerService.save(cnpj, name, businessArea));
        assertEquals(cnpjValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptInvalidFormatCnpj() {
        cnpj = "41.789.228/0001-90";
        String cnpjValidatorExceptionMessage = "Fail! Could not accept this invalid CNPJ! Cause of exception from the validator API: [CNPJError : INVALID FORMAT]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> customerService.save(cnpj, name, businessArea));
        assertEquals(cnpjValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptRepeatedDigitsCnpj() {
        cnpj = "11111111111111";
        String cnpjValidatorExceptionMessage = "Fail! Could not accept this invalid CNPJ! Cause of exception from the validator API: [CNPJError : REPEATED DIGITS, CNPJError : INVALID CHECK DIGITS]";

        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> customerService.save(cnpj, name, businessArea));
        assertEquals(cnpjValidatorExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldFindCustomerByCnpj() {
        customerService.save(cnpj, name, businessArea);
        Customer customer = customerService.findCustomerByCnpj("41789228000190");
        assertEquals(cnpj, customer.getCustomerCnpj());
        assertEquals(name, customer.getCustomerName());
        assertEquals(businessArea, customer.getCustomerBusinessArea());
    }

    @Test
    public void shouldNotFindCustomerByCnpj() {
        String customerNotFoundExceptionMessage = "Fail! Could not find a Customer with this CNPJ!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> customerService.findCustomerByCnpj("41789228000190"));
        assertEquals(customerNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldRemoveCustomerByCnpj() {
        customerService.save(cnpj, name, businessArea);
        Customer customer = customerService.findCustomerByCnpj("41789228000190");
        assertEquals(cnpj, customer.getCustomerCnpj());
        assertEquals(name, customer.getCustomerName());
        assertEquals(businessArea, customer.getCustomerBusinessArea());
        customerService.remove(cnpj);
        String customerNotFoundExceptionMessage = "Fail! Could not find a Customer with this CNPJ!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> customerService.findCustomerByCnpj("41789228000190"));
        assertEquals(customerNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotRemoveCustomerNotFoundByCnpj() {
        String customerNotRemovedExceptionMessage = "Fail! Could not remove a Customer with this CNPJ as it doesn't exist!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> customerService.remove("41789228000190"));
        assertEquals(customerNotRemovedExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotSaveCnpjAlreadyRegistered() {
        customerService.save(cnpj, name, businessArea);
        String duplicateCustomerExceptionMessage = "Fail! Could not create new Customer as this CNPJ is already registered!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> customerService.save(cnpj, name, businessArea));
        assertEquals(duplicateCustomerExceptionMessage, exceptionOne.getMessage());
    }
}
