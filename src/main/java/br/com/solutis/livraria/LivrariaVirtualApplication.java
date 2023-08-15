package br.com.solutis.livraria;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class LivrariaVirtualApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(LivrariaVirtualApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Iniciando a Livraria Virtual!");
        SpringApplication.run(LivrariaVirtualApplication.class, args);
        LOGGER.info("Livraria Virtual iniciada com sucesso!");
    }
}
