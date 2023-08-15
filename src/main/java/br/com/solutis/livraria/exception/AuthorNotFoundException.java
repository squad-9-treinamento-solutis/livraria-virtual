package br.com.solutis.livraria.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(Long id) {
        super("Author with ID " + id + " not found.");
    }
}
