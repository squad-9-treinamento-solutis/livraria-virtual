package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
