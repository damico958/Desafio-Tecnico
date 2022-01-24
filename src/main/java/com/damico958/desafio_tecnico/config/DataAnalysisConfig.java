package com.damico958.desafio_tecnico.config;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataAnalysisConfig {

    @Bean("cpfValidator")
    public CPFValidator cpfValidator() {
        return new CPFValidator();
    }

    @Bean("cnpjValidator")
    public CNPJValidator cnpjValidator() {
        return new CNPJValidator();
    }
}
