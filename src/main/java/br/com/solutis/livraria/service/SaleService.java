package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.domain.PrintedBook;
import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.BookRepository;
import br.com.solutis.livraria.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final BookRepository<Book> bookRepository;

    public Sale addSale(SaleDTO saleDTO) {
        List<Book> books = new ArrayList<>();
        float value = 0;

        for (Long id : saleDTO.getBooksId()) {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("Book Not Found"));

            if (book instanceof PrintedBook) {
                int stock = ((PrintedBook) book).getStock();
                if (stock > 0) {
                    int newStock = ((PrintedBook) book).getStock() - 1;
                    ((PrintedBook) book).setStock(newStock);
                } else {
                    throw new BadRequestException("Book Out of Stock");
                }
            }

            value += book.getPrice();
            books.add(book);
        }

        return saleRepository.save(
                Sale.builder()
                        .clientName(saleDTO.getClientName())
                        .value(value)
                        .books(books)
                        .build());
    }

    public Sale updateSale(SaleDTO sale) {
        findById(sale.getId());

        return saleRepository.save(Sale.builder().clientName(sale.getClientName()).build());
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new BadRequestException("Sale not found"));
    }
}
