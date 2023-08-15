package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.exception.BookNotFoundException;
import br.com.solutis.livraria.exception.BookServiceException;
import br.com.solutis.livraria.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
/*
 * This is a generic service for the usage of eletronic and printed books.
 * */
public class BookService<T extends Book> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final BookRepository<T> bookRepository;

    public T addBook(T book) {
        LOGGER.info("Adding book: {}", book);
        try{
            return bookRepository.save(book);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new BookServiceException("An error occurred while adding book.", e);
        }

    }

    public T updateBook(T book) {
        LOGGER.info("Updating book with ID: {}", book.getId());
        findById(book.getId());

        try{
            return bookRepository.save(book);
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            throw new BookServiceException("An error occurred while updating book.", e);
        }

    }

    public T findById(Long id) {
        LOGGER.info("Finding book with ID: {}", id);

        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<T> findAllBooks() {
        try{
            return bookRepository.findAll();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new BookServiceException("An error occurred while fetching book.", e);
        }

    }

    public void deleteBook(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }
}
