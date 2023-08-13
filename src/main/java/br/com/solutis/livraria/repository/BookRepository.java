package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository<T extends Book> extends JpaRepository<T, Long> {
}
