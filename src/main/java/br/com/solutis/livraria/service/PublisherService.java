package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(PublisherDTO publisherDTO) {
        findById(publisherDTO.getId());
       
        return publisherRepository.save(
                Publisher.builder()
                        .id(publisherDTO.getId())
                        .name(publisherDTO.getName())
                        .build()
        );
    }

    public Publisher findById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new BadRequestException("Publisher not found"));
    }

    public List<Publisher> findAllPublichers() {
        return publisherRepository.findAll();
    }
}
