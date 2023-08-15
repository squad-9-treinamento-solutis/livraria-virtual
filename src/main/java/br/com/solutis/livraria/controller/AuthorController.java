package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
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
    public ResponseEntity<Author> addAuthor(@RequestBody @Valid Author author) {
        return new ResponseEntity<>(authorService.addAuthor(author), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "ATUALIZAR AUTOR", description = "Atualiza o autor")
    public ResponseEntity<Author> updateAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        if (authorDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authorService.updateAuthor(authorDTO), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR OS AUTORES POR ID", description = "Lista autores por id")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        Author author = authorService.findById(id);

        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "LISTAR TODOS OS AUTORES", description = "Lista todos os autores")
    public ResponseEntity<List<Author>> findAllAuthors() {
        List<Author> Authors = authorService.findAllAuthors();

        return new ResponseEntity<>(Authors, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETAR AUTOR", description = "Deleta autor")
    public ResponseEntity<Author> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
