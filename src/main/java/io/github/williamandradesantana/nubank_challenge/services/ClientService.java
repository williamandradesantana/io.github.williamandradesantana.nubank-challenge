package io.github.williamandradesantana.nubank_challenge.services;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientResponse;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.mappers.ClientMapper;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import io.github.williamandradesantana.nubank_challenge.validators.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientValidator clientValidator;

    public List<ClientResponse> getAllClientsWithContacts() {
        return clientRepository.allClientsWithContact().stream().map(clientMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse retrieveOneClientWithContacts(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
        return clientMapper.toResponse(client);
    }

    @Transactional
    public void createClient(ClientRequest request) {
        clientValidator.validate(request);
        Client client = clientMapper.toDomain(request);
        clientRepository.save(client);
    }
}
