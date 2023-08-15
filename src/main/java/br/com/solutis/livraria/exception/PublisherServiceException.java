package br.com.solutis.livraria.exception;

public class PublisherServiceException extends RuntimeException{
    public PublisherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
