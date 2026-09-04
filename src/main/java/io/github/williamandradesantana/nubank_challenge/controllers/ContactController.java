package io.github.williamandradesantana.nubank_challenge.controllers;

import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createContact(@RequestBody ContactRequest request) {
        contactService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
