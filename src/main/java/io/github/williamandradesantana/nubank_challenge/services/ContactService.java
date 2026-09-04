package io.github.williamandradesantana.nubank_challenge.services;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.mappers.ContactMapper;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import io.github.williamandradesantana.nubank_challenge.repositories.ContactRepository;
import io.github.williamandradesantana.nubank_challenge.validators.ContactValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;
    private final ContactValidator contactValidator;

    @Transactional
    public void createContact(ContactRequest request) {
        contactValidator.validate(request);
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + request.clientId()));

        Contact contact = contactMapper.toDomain(request, client);
        client.addContact(contact);
        contactRepository.save(contact);
    }
}
