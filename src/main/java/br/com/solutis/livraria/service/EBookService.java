package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.EBook;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.repository.BookRepository;
import br.com.solutis.livraria.repository.EBookRepository;
import br.com.solutis.livraria.repository.PrintedBookRepository;
import org.springframework.stereotype.Service;

@Service
public class EBookService extends BookService<EBook>{
    private final EBookRepository eBookRepository;

    public EBookService(BookRepository<EBook> bookRepository, EBookRepository eBookRepository) {
        super(bookRepository);
        this.eBookRepository = eBookRepository;
    }

    public int countBooks() {
        return eBookRepository.countBooks();
    }
}
