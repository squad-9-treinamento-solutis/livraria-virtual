package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.EBook;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.repository.PublisherRepository;
import br.com.solutis.livraria.requests.EBookPostRequestBody;
import br.com.solutis.livraria.requests.PrintedBookPostRequestBody;
import br.com.solutis.livraria.service.BookService;
import br.com.solutis.livraria.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {
    private final BookService<EBook> eBookService;
    private final BookService<PrintedBook> printedBookService;
    private final PublisherRepository publisherRepository;

    @PostMapping(path = "/printed")
    public ResponseEntity<PrintedBook> addPrintedBook(@RequestBody @Valid PrintedBookPostRequestBody printedBookPostRequestBody) {
        PrintedBook printedBook = PrintedBook.builder()
                .title(printedBookPostRequestBody.getTitle())
                .price(printedBookPostRequestBody.getPrice())
                .shipment(printedBookPostRequestBody.getShipment())
                .stock(printedBookPostRequestBody.getStock())
                .publisher(
                        publisherRepository.findById(printedBookPostRequestBody.getPublisherId()).orElseThrow()
                )
                .build();

        return new ResponseEntity<>(printedBookService.addBook(printedBook), HttpStatus.CREATED);
    }

    @PostMapping(path = "/eletronic")
    public ResponseEntity<EBook> addEbook(@RequestBody @Valid EBookPostRequestBody eBookPostRequestBody) {
        EBook eBook = EBook.builder()
                .title(eBookPostRequestBody.getTitle())
                .price(eBookPostRequestBody.getPrice())
                .size(eBookPostRequestBody.getSize())
                .publisher(
                        publisherRepository.findById(eBookPostRequestBody.getPublisherId()).orElseThrow()
                )
                .build();

        return new ResponseEntity<>(eBookService.addBook(eBook), HttpStatus.CREATED);
    }
}
