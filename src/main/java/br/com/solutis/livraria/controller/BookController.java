package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.EBook;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.repository.PublisherRepository;
import br.com.solutis.livraria.dto.EBookDTO;
import br.com.solutis.livraria.dto.PrintedBookDTO;
import br.com.solutis.livraria.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<PrintedBook> addPrintedBook(@RequestBody @Valid PrintedBookDTO printedBookDTO) {
        PrintedBook printedBook = PrintedBook.builder()
                .title(printedBookDTO.getTitle())
                .price(printedBookDTO.getPrice())
                .shipment(printedBookDTO.getShipment())
                .stock(printedBookDTO.getStock())
                .publisher(
                        publisherRepository.findById(printedBookDTO.getPublisherId()).orElseThrow()
                )
                .build();

        return new ResponseEntity<>(printedBookService.addBook(printedBook), HttpStatus.CREATED);
    }

    @PostMapping(path = "/eletronic")
    public ResponseEntity<EBook> addEbook(@RequestBody @Valid EBookDTO eBookDTO) {
        EBook eBook = EBook.builder()
                .title(eBookDTO.getTitle())
                .price(eBookDTO.getPrice())
                .size(eBookDTO.getSize())
                .publisher(
                        publisherRepository.findById(eBookDTO.getPublisherId()).orElseThrow()
                )
                .build();

        return new ResponseEntity<>(eBookService.addBook(eBook), HttpStatus.CREATED);
    }

    @PutMapping(path = "/printed")
    @Transactional
    public ResponseEntity<PrintedBook> updatePrintedBook(@RequestBody PrintedBookDTO printedBookDTO){
        PrintedBook printedBook = PrintedBook.builder()
                .title(printedBookDTO.getTitle())
                .price(printedBookDTO.getPrice())
                .shipment(printedBookDTO.getShipment())
                .stock(printedBookDTO.getStock())
                .publisher(
                        publisherRepository.findById(printedBookDTO.getPublisherId()).orElseThrow()
                )
                .build();
        return new ResponseEntity<>(printedBookService.updateBook(printedBook), HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/eletronic")
    @Transactional
    public ResponseEntity<EBook> updatePrintedBook(@RequestBody @Valid EBookDTO eBookDTO){
        EBook eBook = EBook.builder()
                .title(eBookDTO.getTitle())
                .price(eBookDTO.getPrice())
                .size(eBookDTO.getSize())
                .publisher(
                        publisherRepository.findById(eBookDTO.getPublisherId()).orElseThrow()
                )
                .build();

        return new ResponseEntity<>(eBookService.updateBook(eBook), HttpStatus.NO_CONTENT);
    }
}
