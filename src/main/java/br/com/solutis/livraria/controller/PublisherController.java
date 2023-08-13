package br.com.solutis.livraria.controller;

import br.com.solutis.livraria.domain.Publisher;
import br.com.solutis.livraria.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publisher")
@RequiredArgsConstructor
@CrossOrigin
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping()
    public ResponseEntity<Publisher> add(@RequestBody @Valid Publisher publisher) {
        return new ResponseEntity<>(publisherService.addPublisher(publisher), HttpStatus.CREATED);
    }
}
