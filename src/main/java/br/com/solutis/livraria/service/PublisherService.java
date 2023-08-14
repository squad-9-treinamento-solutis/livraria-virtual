package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.exception.BadRequestException;
import br.com.solutis.livraria.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);
    private final PublisherRepository publisherRepository;

    public Publisher addPublisher(Publisher publisher) {
        LOGGER.info("Adding publisher: {}", publisher);
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(PublisherDTO publisherDTO) {
        LOGGER.info("Updating publisher with ID: {}", publisherDTO.getId());
        findById(publisherDTO.getId());

        return publisherRepository.save(
                Publisher.builder()
                        .id(publisherDTO.getId())
                        .name(publisherDTO.getName())
                        .build()
        );
    }

    public Publisher findById(Long id) {
        LOGGER.info("Finding publisher with ID: {}", id);
        return publisherRepository.findById(id).orElseThrow(() -> new BadRequestException("Publisher not found"));
    }
}
