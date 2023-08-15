package br.com.solutis.livraria.exception;

public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException(Long id) {
        super("Publisher with ID " + id + " not found.");
    }
}
