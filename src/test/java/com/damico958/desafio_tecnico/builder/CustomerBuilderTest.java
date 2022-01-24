package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerBuilderTest {

    @Test
    public void shouldBuildSalesmanWithCompleteInfo() {
        Customer customer = CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build();

        assertEquals("86592958000134", customer.getCustomerCnpj());
        assertEquals("Felipe", customer.getCustomerName());
        assertEquals("Desenvolvedor", customer.getCustomerBusinessArea());
    }


    @Test
    public void shouldNotAcceptNullOrNegativeParameters() {
        String nullCnpjExceptionMessage = "Fail! Could not build a customer object with null CNPJ!";
        String nullParameterExceptionMessage = "Fail! Could not build a customer object with null parameter!";
        IllegalArgumentException nullCnpjException = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj(null)
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException nullNameException = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName(null)
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException nullBusinessAreaException = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("Felipe")
                .withBusinessArea(null)
                .build());
        assertEquals(nullCnpjExceptionMessage, nullCnpjException.getMessage());
        assertEquals(nullParameterExceptionMessage, nullNameException.getMessage());
        assertEquals(nullParameterExceptionMessage, nullBusinessAreaException.getMessage());
    }

    @Test
    public void shouldNotAcceptEmptyParameters() {
        String emptyCnpjExceptionMessage = "Fail! Could not build a customer object with empty CNPJ!";
        String emptyParameterExceptionMessage = "Fail! Could not build a customer object with empty parameter!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("")
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("Felipe")
                .withBusinessArea("")
                .build());
        assertEquals(emptyCnpjExceptionMessage, exceptionOne.getMessage());
        assertEquals(emptyParameterExceptionMessage, exceptionTwo.getMessage());
        assertEquals(emptyParameterExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotAcceptDigitsOrLetterInCertainFields() {

        String lettersInCnpjExceptionMessage = "Fail! Could not build a customer object with letters inside CNPJ!";
        String digitsInFieldExceptionMessage = "Fail! Could not build a customer object with digits inside field!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("8659295ABC8000134")
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("F3l1p3")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("Felipe")
                .withBusinessArea("RH321")
                .build());
        assertEquals(lettersInCnpjExceptionMessage, exceptionOne.getMessage());
        assertEquals(digitsInFieldExceptionMessage, exceptionTwo.getMessage());
        assertEquals(digitsInFieldExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildCustomerWithoutCnpj() {
        String missingCnpjExceptionMessage = "Fail! Can't build a Customer object without providing a CNPJ!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withName("Felipe")
                .build());
        assertEquals(missingCnpjExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingCnpjExceptionMessage, exceptionTwo.getMessage());
        assertEquals(missingCnpjExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildCustomerWithoutName() {
        String missingNameExceptionMessage = "Fail! Can't build a Customer object without providing a name!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .build());
        assertEquals(missingNameExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingNameExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldNotBuildCustomerWithoutBusinessArea() {
        String missingBusinessAreaExceptionMessage = "Fail! Can't build a Customer object without providing a business area!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("86592958000134")
                .withName("Felipe")
                .build());
        assertEquals(missingBusinessAreaExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptCnpjWithWrongSize() {
        String wrongCpfSizeExceptionMessage = "Fail! Could not build a customer object with wrong CNPJ size!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("865929580001341111")
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> CustomerBuilder.builder()
                .withCnpj("865")
                .withName("Felipe")
                .withBusinessArea("Desenvolvedor")
                .build());
        assertEquals(wrongCpfSizeExceptionMessage, exceptionOne.getMessage());
        assertEquals(wrongCpfSizeExceptionMessage, exceptionTwo.getMessage());

    }
}
