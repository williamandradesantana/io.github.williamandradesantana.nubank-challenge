package io.github.williamandradesantana.nubank_challenge.services;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientResponse;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.mappers.ClientMapper;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import io.github.williamandradesantana.nubank_challenge.validators.ClientValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock private ClientValidator clientValidator;

    @InjectMocks
    private ClientService clientService;

    @Test
    @DisplayName("Test: Should create client when request is valid")
    void test_WhenRequestIsValid_ShouldCreateClient() {
        ClientRequest request = new ClientRequest("Zeca Ramos");
        Client client = new Client("Zeca Ramos");

        when(clientMapper.toDomain(request)).thenReturn(client);

        clientService.createClient(request);

        verify(clientValidator).validate(request);
        verify(clientRepository).save(client);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when client does not exist")
    void test_WhenClientDoesNotExist_ShouldThrowResourceNotFoundException() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.retrieveOneClientWithContacts(99L));
    }

    @Test
    @DisplayName("Should return all clients mapped to response")
    void test_WhenClientsExist_ShouldReturnAllClientsMappedToResponse() {
        Client client = new Client(1L, "Zeca Ramos", new HashSet<>());
        ClientResponse response = new ClientResponse(1L, "Zeca Ramos", Set.of());

        when(clientRepository.allClientsWithContact()).thenReturn(List.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        List<ClientResponse> result = clientService.getAllClientsWithContacts();

        assertTrue(result.contains(response));
    }
}