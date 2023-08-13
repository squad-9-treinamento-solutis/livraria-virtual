package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public T updateBook(T book){
        Optional<T> savedBook = bookRepository.findById(book.getId());
        if (savedBook != null){
            return bookRepository.save(book);
        }
        return null;
    }
    public Book findById(Long id) {

        return bookRepository.findById(id).orElse(null);

    }
}
