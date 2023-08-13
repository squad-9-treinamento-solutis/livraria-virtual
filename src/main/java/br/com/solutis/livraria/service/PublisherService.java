package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(PublisherDTO publisherDTO){
        Optional<Publisher> savedPublisher = publisherRepository.findById(publisherDTO.getId());
        if (savedPublisher != null){
            return publisherRepository.save(
                    Publisher.builder()
                    .id(publisherDTO.getId())
                    .name(publisherDTO.getName())
                    .build()
            );
        }
        return null;
    }
}
