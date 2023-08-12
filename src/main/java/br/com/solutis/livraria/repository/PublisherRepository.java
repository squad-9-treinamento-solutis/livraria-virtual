package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
