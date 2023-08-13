package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.dto.AuthorDTO;
import br.com.solutis.livraria.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(AuthorDTO authorDTO){
        Optional<Author> savedAuthor = authorRepository.findById(authorDTO.getId());
        if (savedAuthor != null){
            return authorRepository.save(
                    Author.builder()
                            .id(authorDTO.getId())
                            .name(authorDTO.getName())
                            .build()
            );
        }
        return null;
    }

    public Author findById(Long id) {

        return authorRepository.findById(id).orElse(null);

    }
}
