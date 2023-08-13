package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.domain.Book;
import br.com.solutis.livraria.domain.Sale;
import br.com.solutis.livraria.dto.SaleDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.AuthorRepository;
import br.com.solutis.livraria.repository.BookRepository;
import br.com.solutis.livraria.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final BookRepository bookRepository;
    public Sale addSale(SaleDTO saleDTO) {
        try{

            List<Book> books = new ArrayList<>();
            for (Book book: books){
                books.add((Book) bookRepository.findById(book.getId()).
                        orElseThrow(()-> new BadRequestException("Book Not Found")));
                System.out.println(book);
            }
            return saleRepository.save(Sale.builder()
                    .clientName(saleDTO.getClientName())
                    .value(saleDTO.getValue())
                    .books(books)
                    .build());

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Sale updateSale(Sale sale){
        Optional<Sale> savedSale = saleRepository.findById(sale.getId());
        if (savedSale != null){
            return saleRepository.save(sale);
        }
        return null;
    }
}
