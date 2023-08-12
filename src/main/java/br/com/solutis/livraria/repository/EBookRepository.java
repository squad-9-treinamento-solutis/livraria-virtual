package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.EBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EBookRepository extends JpaRepository<EBook, Long> {
}
