package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);
    private final SaleRepository saleRepository;
    private final BookService<Book> bookService;

    public Sale addSale(SaleDTO saleDTO) {
        BooksAndValue booksAndValue = getBookList(saleDTO);

        LOGGER.info("Adding sale: {}", saleDTO);
        return saleRepository.save(
                Sale.builder()
                        .clientName(saleDTO.getClientName())
                        .value(booksAndValue.value)
                        .books(booksAndValue.books)
                        .build());
    }

    public Sale updateSale(SaleDTO saleDTO) {
        Sale savedSale = findById(saleDTO.getId());

        for (Book book : savedSale.getBooks()) {
            if (book instanceof PrintedBook printedBook) {
                bookService.updateBook(
                        PrintedBook.builder()
                                .id(printedBook.getId())
                                .stock(printedBook.getStock() + 1)
                                .shipment(printedBook.getShipment())
                                .publisher(printedBook.getPublisher())
                                .title(printedBook.getTitle())
                                .price(printedBook.getPrice())
                                .build()
                );
            }
        }

        BooksAndValue booksAndValue = getBookList(saleDTO);

        LOGGER.info("Updating sale with ID: {}", saleDTO.getId());
        return saleRepository.save(
                Sale.builder()
                        .id(saleDTO.getId())
                        .clientName(saleDTO.getClientName())
                        .books(booksAndValue.books())
                        .value(booksAndValue.value())
                        .build()
        );
    }

    public Sale findById(Long id) {
        LOGGER.info("Finding sale with ID: {}", id);
        return saleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Sale not found"));
    }

    private BooksAndValue getBookList(SaleDTO sale) {
        List<Book> books = new ArrayList<>();
        float value = 0;

        for (Long id : sale.getBooksId()) {
            Book book = bookService.findById(id);

            if (book instanceof PrintedBook printedBook) {
                int stock = printedBook.getStock();
                if (stock > 0) {
                    printedBook.setStock(printedBook.getStock() - 1);
                } else {
                    LOGGER.error("Book out of Stock with ID: {}", printedBook.getId());
                    throw new BadRequestException("Book Out of Stock");
                }
            }

            value += book.getPrice();
            books.add(book);
        }
        return new BooksAndValue(books, value);
    }

    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    private record BooksAndValue(List<Book> books, float value) {
    }
}
