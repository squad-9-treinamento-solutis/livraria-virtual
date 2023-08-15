package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.exception.AuthorNotFoundException;
import br.com.solutis.livraria.exception.AuthorServiceException;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@CrossOrigin
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @Operation(summary = "CRIAR AUTOR", description = "Cria um autor")
    public ResponseEntity<?> addAuthor(@RequestBody @Valid Author author) {
        try {
            return new ResponseEntity<>(authorService.addAuthor(author), HttpStatus.CREATED);
        } catch (AuthorServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @Transactional
    @Operation(summary = "ATUALIZAR AUTOR", description = "Atualiza o autor")
    public ResponseEntity<?> updateAuthor(@RequestBody @Valid AuthorDTO authorDTO) {

        try {
            return new ResponseEntity<>(authorService.updateAuthor(authorDTO), HttpStatus.NO_CONTENT);
        } catch (AuthorServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error updating author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR OS AUTORES POR ID", description = "Lista autores por id")
    public ResponseEntity<?> findById(@PathVariable Long id) {


        try {
            Author author = authorService.findById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (AuthorNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "LISTAR TODOS OS AUTORES", description = "Lista todos os autores")
    public ResponseEntity<?> findAllAuthors() {

        try {
            List<Author> Authors = authorService.findAllAuthors();
            return new ResponseEntity<>(Authors, HttpStatus.OK);
        } catch (AuthorServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETAR AUTOR", description = "Deleta autor")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {

        try {
            authorService.deleteAuthor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthorNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
