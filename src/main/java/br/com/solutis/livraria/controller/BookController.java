package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.*;
import br.com.solutis.livraria.dto.EBookDTO;
import br.com.solutis.livraria.dto.PrintedBookDTO;
import br.com.solutis.livraria.exception.BookNotFoundException;
import br.com.solutis.livraria.exception.BookServiceException;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.exception.MaxLimitExceededException;
import br.com.solutis.livraria.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {
    private static final int MAX_IMPRESSOS = 10;
    private static final int MAX_ELETRONICOS = 20;

    private final BookService<Book> bookService;
    private final EBookService eBookService;
    private final PrintedBookService printedBookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;


    @PostMapping(path = "/printed")
    @Operation(summary = "CRIAR LIVRO IMPRESSO", description = "Cria um livro impresso")
    public ResponseEntity<?> addPrintedBook(@RequestBody @Valid PrintedBookDTO printedBookDTO) {


        try {

            int printedBooks = printedBookService.countBooks();

            if (printedBooks >= MAX_IMPRESSOS) {
                throw new MaxLimitExceededException("Maximum number of printed books reached. Maximum number is: " + MAX_IMPRESSOS);
            }
            List<Author> authors = getAuthorsFromIds(printedBookDTO.getAuthorsId());
            Publisher publisher = publisherService.findById(printedBookDTO.getPublisherId());

            PrintedBook printedBook = PrintedBook.builder()
                    .title(printedBookDTO.getTitle())
                    .price(printedBookDTO.getPrice())
                    .shipment(printedBookDTO.getShipment())
                    .stock(printedBookDTO.getStock())
                    .authors(authors)
                    .publisher(publisher)
                    .build();

            return new ResponseEntity<>(printedBookService.addBook(printedBook), HttpStatus.CREATED);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding printed book: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path = "/eletronic")
    @Operation(summary = "CRIAR EBOOK", description = "Cria o livro eletronico")
    public ResponseEntity<?> addEbook(@RequestBody @Valid EBookDTO eBookDTO) {


        try {
            int eBooks = eBookService.countBooks();

            if (eBooks >= MAX_ELETRONICOS) {
                throw new MaxLimitExceededException("Maximum number of ebooks reached. Maximum number is: " + MAX_ELETRONICOS);
            }
            List<Author> authors = getAuthorsFromIds(eBookDTO.getAuthorsId());
            Publisher publisher = publisherService.findById(eBookDTO.getPublisherId());

            EBook eBook = EBook.builder()
                    .title(eBookDTO.getTitle())
                    .price(eBookDTO.getPrice())
                    .size(eBookDTO.getSize())
                    .authors(authors)
                    .publisher(publisher)
                    .build();
            return new ResponseEntity<>(eBookService.addBook(eBook), HttpStatus.CREATED);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding eletronic book: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping(path = "/printed")
    @Transactional
    @Operation(summary = "ATUALIZAR LIVRO IMPRESSO", description = "Atualiza o livro impresso")
    public ResponseEntity<?> updatePrintedBook(@RequestBody PrintedBookDTO printedBookDTO) {


        try {
            List<Author> authors = getAuthorsFromIds(printedBookDTO.getAuthorsId());
            Publisher publisher = publisherService.findById(printedBookDTO.getPublisherId());

            PrintedBook printedBook = PrintedBook.builder()
                    .id(printedBookDTO.getId())
                    .title(printedBookDTO.getTitle())
                    .price(printedBookDTO.getPrice())
                    .shipment(printedBookDTO.getShipment())
                    .stock(printedBookDTO.getStock())
                    .authors(authors)
                    .publisher(publisher)
                    .build();
            return new ResponseEntity<>(printedBookService.updateBook(printedBook), HttpStatus.NO_CONTENT);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error updating printed book: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(path = "/eletronic")
    @Transactional
    @Operation(summary = "ATUALIZAR EBOOK", description = "Atualiza o livro eletronico")
    public ResponseEntity<?> updateEletronicBook(@RequestBody @Valid EBookDTO eBookDTO) {


        try {
            List<Author> authors = getAuthorsFromIds(eBookDTO.getAuthorsId());
            Publisher publisher = publisherService.findById(eBookDTO.getPublisherId());

            EBook eBook = EBook.builder()
                    .id(eBookDTO.getId())
                    .title(eBookDTO.getTitle())
                    .price(eBookDTO.getPrice())
                    .size(eBookDTO.getSize())
                    .authors(authors)
                    .publisher(publisher)
                    .build();

            return new ResponseEntity<>(eBookService.updateBook(eBook), HttpStatus.NO_CONTENT);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error updating printed book: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "LISTAR OS LIVROS POR ID", description = "Lista livros por id")
    public ResponseEntity<?> findById(@PathVariable Long id) {


        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    @Operation(summary = "LISTAR TODOS OS LIVROS", description = "Lista todos os livros")
    public ResponseEntity<?> findAllBooks() {

        try {
            List<Book> Books = bookService.findAllBooks();
            return new ResponseEntity<>(Books, HttpStatus.OK);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETAR LIVRO", description = "Deleta livro")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {

        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    private List<Author> getAuthorsFromIds(List<Long> authorsIds) {


        List<Author> authors = new ArrayList<>();

        for (Long authorId : authorsIds) {
            authors.add(authorService.findById(authorId));
        }

        return authors;
    }

}
