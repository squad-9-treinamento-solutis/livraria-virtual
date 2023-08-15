package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.*;
import br.com.solutis.livraria.dto.EBookDTO;
import br.com.solutis.livraria.dto.PrintedBookDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.service.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Book book = bookService.findById(id);

        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> Books = bookService.findAllBooks();

        return new ResponseEntity<>(Books, HttpStatus.OK);
    }

    @PostMapping(path = "/printed")
    public ResponseEntity<PrintedBook> addPrintedBook(@RequestBody @Valid PrintedBookDTO printedBookDTO) {
        int printedBooks = printedBookService.countBooks();

        if (printedBooks >= MAX_IMPRESSOS) {
            throw new BadRequestException("Maximum number of printed books reached. Maximum number is: " + MAX_IMPRESSOS);
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
    }

    @PostMapping(path = "/eletronic")
    public ResponseEntity<EBook> addEbook(@RequestBody @Valid EBookDTO eBookDTO) {
        int eBooks = eBookService.countBooks();

        if (eBooks >= MAX_ELETRONICOS) {
            throw new BadRequestException("Maximum number of ebooks reached. Maximum number is: " + MAX_ELETRONICOS);
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
    }

    @PutMapping(path = "/printed")
    @Transactional
    public ResponseEntity<PrintedBook> updatePrintedBook(@RequestBody PrintedBookDTO printedBookDTO) {
        if (printedBookDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
    }

    @PutMapping(path = "/eletronic")
    @Transactional
    public ResponseEntity<EBook> updateEletronicBook(@RequestBody @Valid EBookDTO eBookDTO) {
        if (eBookDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<Author> getAuthorsFromIds(List<Long> authorsIds) {
        if (authorsIds == null) {
            return null;
        }

        List<Author> authors = new ArrayList<>();

        for (Long authorId : authorsIds) {
            authors.add(authorService.findById(authorId));
        }

        return authors;
    }
}
