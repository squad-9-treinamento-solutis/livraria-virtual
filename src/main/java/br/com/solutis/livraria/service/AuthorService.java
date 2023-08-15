package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.exception.AuthorNotFoundException;
import br.com.solutis.livraria.exception.AuthorServiceException;
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
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AuthorServiceException("An error occurred while adding author.", e);
        }
    }

    public Author updateAuthor(AuthorDTO authorDTO) {
        LOGGER.info("Updating author with ID: {}", authorDTO.getId());
        findById(authorDTO.getId());

        try {
            return authorRepository.save(
                    Author.builder()
                            .id(authorDTO.getId())
                            .name(authorDTO.getName())
                            .build()
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AuthorServiceException("An error occurred while updating author.", e);
        }
    }

    public Author findById(Long id) {
        LOGGER.info("Finding author with ID: {}", id);

        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public List<Author> findAllAuthors() {
        try {
            return authorRepository.findAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AuthorServiceException("An error occurred while fetching authors.", e);
        }
    }

    public void deleteAuthor(Long id) {
        findById(id);
        authorRepository.deleteById(id);
    }
}
