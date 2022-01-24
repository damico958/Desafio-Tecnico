package com.damico958.desafio_tecnico;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public CPFValidator cpfValidator() {
        return new CPFValidator();
    }

    @Bean
    public CNPJValidator cnpjValidator() {
        return new CNPJValidator();
    }
}
