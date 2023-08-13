package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.EBook;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {
    private final BookService<EBook> eBookService;
    private final BookService<PrintedBook> printedBookService;

    @PostMapping(path = "/printed")
    public ResponseEntity<PrintedBook> addPrintedBook(@RequestBody @Valid PrintedBook printedBook) {
        return new ResponseEntity<>(printedBookService.addBook(printedBook), HttpStatus.CREATED);
    }

    @PostMapping(path = "/eletronic")
    public ResponseEntity<EBook> addEbook(@RequestBody @Valid EBook eBook) {
        return new ResponseEntity<>(eBookService.addBook(eBook), HttpStatus.CREATED);
    }
}
