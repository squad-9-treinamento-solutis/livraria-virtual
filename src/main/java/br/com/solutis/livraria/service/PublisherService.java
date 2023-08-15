package br.com.solutis.livraria.service;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.exception.PublisherServiceException;
import br.com.solutis.livraria.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);
    private final PublisherRepository publisherRepository;

    public Publisher addPublisher(Publisher publisher) {
        LOGGER.info("Adding publisher: {}", publisher);
        try {
            return publisherRepository.save(publisher);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PublisherServiceException("An error occurred while adding publisher", e);
        }

    }

    public Publisher updatePublisher(PublisherDTO publisherDTO) {
        LOGGER.info("Updating publisher with ID: {}", publisherDTO.getId());
        findById(publisherDTO.getId());

        try {
            return publisherRepository.save(
                    Publisher.builder()
                            .id(publisherDTO.getId())
                            .name(publisherDTO.getName())
                            .build()
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PublisherServiceException("An error occurred while updating publisher", e);
        }

    }

    public Publisher findById(Long id) {
        LOGGER.info("Finding publisher with ID: {}", id);
        return publisherRepository.findById(id).orElseThrow(() -> new BadRequestException("Publisher not found"));
    }

    public List<Publisher> findAllPublishers() {
        try {
            return publisherRepository.findAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PublisherServiceException("An error occurred while fetching publishers.", e);
        }

    }

    public void deletePublisher(Long id) {
        findById(id);
        publisherRepository.deleteById(id);
    }
}
