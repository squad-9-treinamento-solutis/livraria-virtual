package br.com.solutis.livraria.repository;

import br.com.solutis.livraria.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT COUNT(*) FROM Sale")
    int countSales();
}
