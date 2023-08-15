package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.exception.SaleServiceException;
import br.com.solutis.livraria.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin
public class SaleController {

    private static final int MAX_VENDAS = 50;

    private final SaleService saleService;

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR VENDA POR ID", description = "Lista a venda por Id")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);

        if (sale != null) {
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "LISTAR TODAS AS VENDAS", description = "Lista todas as venda")
    public ResponseEntity<List<Sale>> findAllSales() {
        List<Sale> sales = saleService.findAllSales();

        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "CRIAR VENDA", description = "Cria uma venda")
    public ResponseEntity<?> addSale(@RequestBody @Valid SaleDTO saleDTO) {
        try {
            return new ResponseEntity<>(saleService.addSale(saleDTO), HttpStatus.CREATED);
        } catch (SaleServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding sale: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    @Transactional
    @Operation(summary = "ATUALIZAR VENDA", description = "Atualizar venda")
    public ResponseEntity<?> updateSale(@RequestBody @Valid SaleDTO saleDTO) {
        try {
            return new ResponseEntity<>(saleService.updateSale(saleDTO), HttpStatus.NO_CONTENT);
        } catch (SaleServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error updating sale: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR VENDA POR ID", description = "Lista a venda por Id")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Sale sale = saleService.findById(id);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (SaleServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "DELETAR VENDA", description = "Deletar venda")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {


        try {
            saleService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SaleServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    @Operation(summary = "LISTAR TODAS AS VENDAS", description = "Lista todas as vendas")
    public ResponseEntity<?> findAllSales() {

        try {
            List<Sale> sales = saleService.findAllSales();
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } catch (SaleServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
