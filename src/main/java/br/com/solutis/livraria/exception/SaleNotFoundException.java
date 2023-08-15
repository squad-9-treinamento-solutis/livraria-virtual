package br.com.solutis.livraria.exception;

public class SaleNotFoundException extends RuntimeException{
    public SaleNotFoundException(Long id) {
        super("Sale with ID " + id + " not found.");
    }
}
