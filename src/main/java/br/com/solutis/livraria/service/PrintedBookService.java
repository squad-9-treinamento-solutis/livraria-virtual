package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.repository.BookRepository;
import br.com.solutis.livraria.repository.PrintedBookRepository;
import org.springframework.stereotype.Service;

@Service
public class PrintedBookService extends BookService<PrintedBook>{
    private final PrintedBookRepository printedBookBookRepository;

    public PrintedBookService(BookRepository<PrintedBook> bookRepository, PrintedBookRepository printedBookBookRepository) {
        super(bookRepository);
        this.printedBookBookRepository = printedBookBookRepository;
    }

    public int countBooks() {
        return printedBookBookRepository.countBooks();
    }
}
