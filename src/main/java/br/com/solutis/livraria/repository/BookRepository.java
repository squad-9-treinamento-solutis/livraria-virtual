package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository<T extends Book> extends JpaRepository<T, Long> {
}
