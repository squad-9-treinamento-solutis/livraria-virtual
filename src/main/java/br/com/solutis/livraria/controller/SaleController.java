package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin
public class SaleController {
    private static final int MAX_VENDAS = 50;

    private final SaleService saleService;

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);

        if (sale != null) {
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Sale>> findAllSales() {
        List<Sale> sales = saleService.findAllSales();

        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sale> addSale(@RequestBody @Valid SaleDTO saleDTO) {
        if (saleService.countSales() >= MAX_VENDAS) {
            throw new BadRequestException("Maximum number of sales reached. Maximum number is: " + MAX_VENDAS);
        }

        return new ResponseEntity<>(saleService.addSale(saleDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Sale> updateSale(@RequestBody @Valid SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.updateSale(saleDTO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
