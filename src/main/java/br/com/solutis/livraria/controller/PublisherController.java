package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.exception.PublisherNotFoundException;
import br.com.solutis.livraria.exception.PublisherServiceException;
import br.com.solutis.livraria.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@CrossOrigin
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping()
    @Operation(summary = "CRIAR EDITORA", description = "Cria uma editora")
    public ResponseEntity<?> addPublisher(@RequestBody @Valid Publisher publisher) {

        try {
            return new ResponseEntity<>(publisherService.addPublisher(publisher), HttpStatus.CREATED);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    @Transactional
    @Operation(summary = "ATUALIZAR EDITORA", description = "Atualiza a editora")
    public ResponseEntity<?> updatePublisher(@RequestBody @Valid PublisherDTO publisherDTO) {

        try {
            return new ResponseEntity<>(publisherService.updatePublisher(publisherDTO), HttpStatus.NO_CONTENT);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR AS EDITORAS POR ID", description = "Lista as editoras por id")
    public ResponseEntity<?> findById(@PathVariable Long id) {


        try {
            Publisher publisher = publisherService.findById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (PublisherNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETAR A EDITORA", description = "deleta a editora")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {


        try {
            publisherService.deletePublisher(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PublisherNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    @Operation(summary = "LISTAR TODAS AS EDITORAS", description = "Lista todas as editoras")
    public ResponseEntity<?> findAllPublishers() {


        try {
            List<Publisher> publishers = publisherService.findAllPublishers();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
