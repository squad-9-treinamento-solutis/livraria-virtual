package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(AuthorDTO authorDTO) {
        findById(authorDTO.getId());
       
        return authorRepository.save(
                Author.builder()
                        .id(authorDTO.getId())
                        .name(authorDTO.getName())
                        .build()
        );
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new BadRequestException("Author not found"));
    }
}
