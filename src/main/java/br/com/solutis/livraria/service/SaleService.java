package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.SaleNotFoundException;
import br.com.solutis.livraria.exception.SaleServiceException;
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

        try {
            return saleRepository.save(
                    Sale.builder()
                            .clientName(saleDTO.getClientName())
                            .value(booksAndValue.value)
                            .books(booksAndValue.books)
                            .build());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SaleServiceException("An error occurred while adding sale", e);
        }

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

        try {
            return saleRepository.save(
                    Sale.builder()
                            .id(saleDTO.getId())
                            .clientName(saleDTO.getClientName())
                            .books(booksAndValue.books())
                            .value(booksAndValue.value())
                            .build()
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SaleServiceException("An error occurred while updating sale", e);
        }

    }

    public Sale findById(Long id) {
        LOGGER.info("Finding sale with ID: {}", id);
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
    }

    private BooksAndValue getBookList(SaleDTO sale) {
        List<Book> books = new ArrayList<>();
        float value = 0;

        for (Long id : sale.getBooksId()) {
            Book book = bookService.findById(id);

            if (book instanceof PrintedBook printedBook) {

                try {
                    int stock = printedBook.getStock();
                    if (stock > 0) {
                        printedBook.setStock(printedBook.getStock() - 1);
                    }
                } catch (Exception e) {
                    LOGGER.error("Book out of Stock with ID: {}", printedBook.getId());
                    throw new SaleServiceException("An error occurred while fetching publishers.", e);
                }

            }

            value += book.getPrice();
            books.add(book);
        }
        try {
            return new BooksAndValue(books, value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SaleServiceException("An error occurred while fetching books.", e);
        }

    }

    public List<Sale> findAllSales() {
        try {

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SaleServiceException("An error occurred while fetching sales.", e);
        }
        return saleRepository.findAll();
    }

    public void deleteSale(Long id) {
        findById(id);
        saleRepository.deleteById(id);
    }

    public int countSales() {
        return saleRepository.countSales();

    }

    private record BooksAndValue(List<Book> books, float value) {


    }
}
