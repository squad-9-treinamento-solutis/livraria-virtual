package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }
}
