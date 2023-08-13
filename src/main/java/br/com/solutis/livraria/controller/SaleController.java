package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.service.AuthorService;
import br.com.solutis.livraria.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<Sale> addSale(@RequestBody @Valid SaleDTO saleDTO){

        return new ResponseEntity<>(saleService.addSale(saleDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Sale> updateSale(@RequestBody @Valid SaleDTO saleDTO){
        if (saleDTO.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(saleService.addSale(saleDTO), HttpStatus.CREATED);
    }

}