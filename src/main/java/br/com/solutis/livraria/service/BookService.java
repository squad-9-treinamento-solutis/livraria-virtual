package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/*
 * This is a generic service for the usage of eletronic and printed books.
 * */
public class BookService<T extends Book> {
    private final BookRepository<T> bookRepository;

    public T addBook(T book) {
        return bookRepository.save(book);
    }

    public T updateBook(T book) {
        T savedBook = findById(book.getId());
        return bookRepository.save(savedBook);
    }

    public T findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BadRequestException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
