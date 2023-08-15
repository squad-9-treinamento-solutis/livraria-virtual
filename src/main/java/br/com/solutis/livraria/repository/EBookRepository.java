package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.EBook;
import org.springframework.data.jpa.repository.Query;

public interface EBookRepository extends BookRepository<EBook> {
    @Query("SELECT COUNT(*) FROM EBook")
    int countBooks();
}
