package io.github.williamandradesantana.nubank_challenge.services;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.mappers.ContactMapper;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import io.github.williamandradesantana.nubank_challenge.repositories.ContactRepository;
import io.github.williamandradesantana.nubank_challenge.validators.ContactValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private ContactValidator contactValidator;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private Client client;
    private ContactRequest request;

    @BeforeEach
    void setUp() {
        // Given - Arrange
        client = new Client(1L, "client-test", new HashSet<>());
        contact = new Contact();
        request = new ContactRequest("11111", client.getId());
    }

    @AfterEach
    void afterEach() {
        client = null;
        contact = null;
        request = null;
        contactRepository = null;
        clientRepository = null;
    }

    @Test
    @DisplayName("Test: must be create a contact and associate it with a client")
    void test_MustBeCreateAContactAndAssociateItWithAClient() {
        contact = new Contact(request.phone(), client);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contactMapper.toDomain(request, client)).thenReturn(contact);

        contactService.createContact(request);

        verify(contactValidator).validate(request);
        verify(contactRepository).save(contact);
        assertTrue(client.getContacts().contains(contact));
    }

    @Test
    @DisplayName("Test: must be throw ResourceNotFoundException if client not exists")
    void test_MustBeThrowResourceNotFoundExceptionIfClientNotExists() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.createContact(request));
        verify(contactRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test: must be throw BusinessException if phone is null")
    void test_MustBeThrowBusinessExceptionIfPhoneIsNull() {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        request = new ContactRequest(null, client.getId());

        doThrow(new BusinessException(expectedErrorMessage)).when(contactValidator).validate(request);

        assertThrows(BusinessException.class, () -> contactService.createContact(request));
        verifyNoInteractions(clientRepository, contactRepository, contactMapper);
    }

    @Test
    @DisplayName("Test: must be throw BusinessException if phone is blank")
    void test_MustBeThrowBusinessExceptionIfPhoneIsBlank() {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        request = new ContactRequest("", client.getId());

        doThrow(new BusinessException(expectedErrorMessage)).when(contactValidator).validate(request);

        assertThrows(BusinessException.class, () -> contactService.createContact(request));
        verifyNoInteractions(clientRepository, contactRepository, contactMapper);
    }

    @Test
    @DisplayName("Test: must be throw BusinessException if phone is blank")
    void test_MustBeThrowBusinessExceptionIfPhoneExceededTheCharacterLimit() {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        request = new ContactRequest("122332131231231231231231231231231231231231231231", client.getId());

        doThrow(new BusinessException(expectedErrorMessage)).when(contactValidator).validate(request);

        assertThrows(BusinessException.class, () -> contactService.createContact(request));
        verifyNoInteractions(clientRepository, contactRepository, contactMapper);
    }

    @Test
    @DisplayName("Test: must be throw BusinessException if client id is null")
    void test_MustBeThrowBusinessExceptionIfClientIdIsNull() {
        String expectedErrorMessage = "Client id is required";
        request = new ContactRequest(request.phone(), null);

        doThrow(new BusinessException(expectedErrorMessage)).when(contactValidator).validate(request);

        assertThrows(BusinessException.class, () -> contactService.createContact(request));
        verifyNoInteractions(clientRepository, contactRepository, contactMapper);
    }
}