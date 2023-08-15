package br.com.solutis.livraria.exception;

public class BookServiceException extends RuntimeException{
    public BookServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
