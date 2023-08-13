package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Author;
import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Publisher> updatePublisher(@RequestBody @Valid PublisherDTO publisherDTO){
        if (publisherDTO.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(publisherService.updatePublisher(publisherDTO), HttpStatus.NO_CONTENT);
    }
}
