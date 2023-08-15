package br.com.solutis.livraria.exception;

public class SaleServiceException extends RuntimeException{
    public SaleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
