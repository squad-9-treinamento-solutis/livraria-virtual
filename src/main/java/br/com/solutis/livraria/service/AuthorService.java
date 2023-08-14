package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;

    public Author addAuthor(Author author) {
        LOGGER.info("Adding author: {}", author.getName());
        return authorRepository.save(author);
    }

    public Author updateAuthor(AuthorDTO authorDTO) {
        LOGGER.info("Updating author with ID: {}", authorDTO.getId());
        findById(authorDTO.getId());

        return authorRepository.save(
                Author.builder()
                        .id(authorDTO.getId())
                        .name(authorDTO.getName())
                        .build()
        );
    }

    public Author findById(Long id) {
        LOGGER.info("Finding author with ID: {}", id);
        return authorRepository.findById(id).orElseThrow(() -> new BadRequestException("Author not found"));
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
