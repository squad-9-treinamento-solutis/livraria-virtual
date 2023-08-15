package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.dto.PublisherDTO;
import br.com.solutis.livraria.exception.ErrorResponse;
import br.com.solutis.livraria.exception.PublisherNotFoundException;
import br.com.solutis.livraria.exception.PublisherServiceException;
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
    public ResponseEntity<?> addPublisher(@RequestBody @Valid Publisher publisher) {

        try {
            return new ResponseEntity<>(publisherService.addPublisher(publisher), HttpStatus.CREATED);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updatePublisher(@RequestBody @Valid PublisherDTO publisherDTO) {

        try {
            return new ResponseEntity<>(publisherService.updatePublisher(publisherDTO), HttpStatus.NO_CONTENT);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse("Error adding author: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {


        try {
            Publisher publisher = publisherService.findById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (PublisherNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {


        try {
            publisherService.deletePublisher(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PublisherNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<?> findAllPublishers() {


        try {
            List<Publisher> publishers = publisherService.findAllPublishers();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (PublisherServiceException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
