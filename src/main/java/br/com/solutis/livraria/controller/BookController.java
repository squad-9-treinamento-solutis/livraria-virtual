package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.*;
import br.com.solutis.livraria.dto.EBookDTO;
import br.com.solutis.livraria.dto.PrintedBookDTO;
import br.com.solutis.livraria.exception.BookNotFoundException;
import br.com.solutis.livraria.exception.BookServiceException;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.service.AuthorService;
import br.com.solutis.livraria.service.BookService;
import br.com.solutis.livraria.service.PublisherService;
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

    private final BookService<Book> bookService;
    private final BookService<EBook> eBookService;
    private final BookService<PrintedBook> printedBookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    @PostMapping(path = "/printed")
    public ResponseEntity<?> addPrintedBook(@RequestBody @Valid PrintedBookDTO printedBookDTO) {


        try {
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
    public ResponseEntity<?> addEbook(@RequestBody @Valid EBookDTO eBookDTO) {


        try {
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
    public ResponseEntity<?> findById(@PathVariable Long id) {


        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<?> findAllBooks() {


        try {
            List<Book> Books = bookService.findAllBooks();
            return new ResponseEntity<>(Books, HttpStatus.OK);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
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
