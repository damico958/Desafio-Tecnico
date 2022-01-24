package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Salesman;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SalesmanBuilderTest {

    @Test
    public void shouldBuildSalesmanWithCompleteInfo() {
        BigDecimal salary = new BigDecimal("1000.00");
        Salesman salesman = SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("Ricardo")
                .withSalary(salary)
                .build();

        assertEquals("49516307027", salesman.getSalesmanCpf());
        assertEquals("Ricardo", salesman.getSalesmanName());
        assertEquals(salary, salesman.getSalesmanSalary());
        assertEquals(BigDecimal.ZERO, salesman.getSalesTotal());
    }

    @Test
    public void shouldNotAcceptNullOrNegativeParameters() {
        String nullCpfExceptionMessage = "Fail! Could not build a salesman object with null CPF!";
        String nullParameterExceptionMessage = "Fail! Could not build a salesman object with null parameter!";
        String nullOrNegativeSalaryExceptionMessage = "Fail! Could not build a salesman object with negative or null salary!";
        IllegalArgumentException nullCpfException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf(null)
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException nullNameException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName(null)
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException nullSalaryException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("Felipe")
                .withSalary(null)
                .build());
        IllegalArgumentException negativeSalaryException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("Felipe")
                .withSalary(new BigDecimal("-1.00"))
                .build());
        assertEquals(nullCpfExceptionMessage, nullCpfException.getMessage());
        assertEquals(nullParameterExceptionMessage, nullNameException.getMessage());
        assertEquals(nullOrNegativeSalaryExceptionMessage, nullSalaryException.getMessage());
        assertEquals(nullOrNegativeSalaryExceptionMessage, negativeSalaryException.getMessage());
    }

    @Test
    public void shouldNotAcceptEmptyParameters() {
        String emptyCpfExceptionMessage = "Fail! Could not build a salesman object with empty CPF!";
        String emptyParameterExceptionMessage = "Fail! Could not build a salesman object with empty parameter!";
        IllegalArgumentException emptyCpfException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("")
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException emptyNameException = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        assertEquals(emptyCpfExceptionMessage, emptyCpfException.getMessage());
        assertEquals(emptyParameterExceptionMessage, emptyNameException.getMessage());
    }

    @Test
    public void shouldNotAcceptDigitsOrLettersInCertainFields() {
        String lettersInCpfExceptionMessage = "Fail! Could not build a salesman object with letters inside CPF!";
        String digitsInFieldExceptionMessage = "Fail! Could not build a salesman object with digits inside field!";
        String lettersInBigDecimalExceptionMessage = "Character A is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark.";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("F3l1p3")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516AD07027")
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("Felipe")
                .withSalary(new BigDecimal("100ABC0.00"))
                .build());
        assertEquals(digitsInFieldExceptionMessage, exceptionOne.getMessage());
        assertEquals(lettersInCpfExceptionMessage, exceptionTwo.getMessage());
        assertEquals(lettersInBigDecimalExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildSalesmanWithoutCpf() {
        String missingCpfExceptionMessage = "Fail! Can't build a Salesman object without providing a CPF!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withName("Felipe")
                .build());
        assertEquals(missingCpfExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingCpfExceptionMessage, exceptionTwo.getMessage());
        assertEquals(missingCpfExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildSalesmanWithoutName() {
        String missingNameExceptionMessage = "Fail! Can't build a Salesman object without providing a name!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .build());
        assertEquals(missingNameExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingNameExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldNotBuildSalesmanWithoutSalary() {
        String missingSalaryExceptionMessage = "Fail! Can't build a Salesman object without providing a salary!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("49516307027")
                .withName("Felipe")
                .build());
        assertEquals(missingSalaryExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotAcceptCpfWithWrongSize() {
        String wrongCpfSizeExceptionMessage = "Fail! Could not build a salesman object with wrong CPF size!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("48397381099111")
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SalesmanBuilder.builder()
                .withCpf("483")
                .withName("Felipe")
                .withSalary(new BigDecimal("1000.00"))
                .build());
        assertEquals(wrongCpfSizeExceptionMessage, exceptionOne.getMessage());
        assertEquals(wrongCpfSizeExceptionMessage, exceptionTwo.getMessage());
    }
}
