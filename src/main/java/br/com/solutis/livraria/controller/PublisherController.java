package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@CrossOrigin
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping()
    public ResponseEntity<Publisher> addPublisher(@RequestBody @Valid Publisher publisher) {

        return new ResponseEntity<>(publisherService.addPublisher(publisher), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Publisher> updatePublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        if (publisherDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(publisherService.updatePublisher(publisherDTO), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> findById(@PathVariable Long id) {

        Publisher publisher = publisherService.findById(id);

        if (publisher != null) {
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> findAllPublishers() {
        List<Publisher> publishers = publisherService.findAllPublishers();

        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
}
