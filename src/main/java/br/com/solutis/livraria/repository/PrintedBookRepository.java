package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.PrintedBook;
import org.springframework.data.jpa.repository.Query;

public interface PrintedBookRepository extends BookRepository<PrintedBook> {
    @Query("SELECT COUNT(*) FROM PrintedBook")
    int countBooks();
}
