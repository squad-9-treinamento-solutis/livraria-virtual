package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.PrintedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintedBookRepository extends JpaRepository<PrintedBook, Long> {
}
