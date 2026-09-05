package io.github.williamandradesantana.nubank_challenge.repositories;

import io.github.williamandradesantana.nubank_challenge.PostgresIntegrationTest;
import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRepositoryTest extends PostgresIntegrationTest {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ClientRepository clientRepository;

    private Contact contact;
    private Client client;

    @BeforeEach
    void setUp() {
        // Given - Arrange
        client = new Client(null, "full-name-test", new HashSet<>());
        clientRepository.save(client);

        contact = new Contact("1111111111", client);
        contactRepository.save(contact);
    }

    @AfterEach
    void afterEach() {
        client = null;
        contact = null;
        contactRepository = null;
        clientRepository = null;
    }

    @Test
    @DisplayName("Test: Should return true when phone is already registered")
    void test_WhenPhoneIsAlreadyRegistered_ShouldReturnTrue() {
        Contact duplicateContact = new Contact("1111111111", client);

        assertTrue(contactRepository.existsByPhone(duplicateContact.getPhone()));
    }

    @Test
    @DisplayName("Test: Should return false when phone not exists")
    void test_WhenPhoneNotExists_ShouldReturnFalse() {
        assertFalse(contactRepository.existsByPhone("22222222"));
    }

    @Test
    @DisplayName("Test: Must prevent duplicate phone numbers.")
    void test_MustPreventDuplicatePhoneNumbers() {
        Contact duplicateContact = new Contact("1111111111", client);
        assertThrows(DataIntegrityViolationException.class, () -> contactRepository.save(duplicateContact));
    }
}