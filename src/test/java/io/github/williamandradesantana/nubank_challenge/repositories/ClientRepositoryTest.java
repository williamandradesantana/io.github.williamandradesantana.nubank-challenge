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

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest extends PostgresIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        // Given - Arrange
        client = new Client(null, "full-name-test", new HashSet<>());
        client.addContact(new Contact("11111111", client));
        client.addContact(new Contact("2222222", client));
    }

    @AfterEach()
    void afterEach() {
        client = null;
    }

    @Test
    @DisplayName("Test: must be client with contacts using fetch join")
    void test_WhenClientsAreSaved_ShouldReturnClientsWithContacts() {
        int expectedQuantityClients = 1;
        String expectedFullName = "full-name-test";

        clientRepository.save(client);

        List<Client> clients = clientRepository.allClientsWithContact();

        assertFalse(clients.isEmpty());
        assertEquals(expectedQuantityClients, clients.size(),
                () -> "Expected number of clients with contacts does not match");
        assertEquals(expectedFullName, clients.getFirst().getFullName(), () -> "Full name not matches");
    }
}