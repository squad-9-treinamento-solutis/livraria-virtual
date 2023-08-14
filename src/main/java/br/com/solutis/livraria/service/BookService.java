package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.exception.BadRequestException;
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
        return bookRepository.save(book);
    }

    public T updateBook(T book) {
        LOGGER.info("Updating book with ID: {}", book.getId());
        findById(book.getId());
        return bookRepository.save(book);
    }

    public T findById(Long id) {
        LOGGER.info("Finding book with ID: {}", id);
        return bookRepository.findById(id).orElseThrow(() -> new BadRequestException("Book not found"));
    }

    public List<T> findAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
