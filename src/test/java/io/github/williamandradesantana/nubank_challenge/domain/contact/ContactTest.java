package io.github.williamandradesantana.nubank_challenge.domain.contact;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContactTest {

    private Contact contact;
    private Client client;

    @BeforeEach
    void setUp() {
        // Given - Arrange
        client = new Client(1L, "full-name-test", new HashSet<>());
        contact = new Contact("1111111111", client);
    }

    @AfterEach
    void afterEach() {
        client = null;
        contact = null;
    }

    @Test
    @DisplayName("Test: create a contact with successfully and returns all attributes corrects")
    void test_WhenCreatedAContact_ShouldReturnAllAttributesCorrects() {
        String expectedPhone = "1111111111";
        Client expectedClient = client;
        String expectedClientName = "full-name-test";

        // When - Act
        // Then - Assert
        assertNotNull(contact, () -> "Contact cannot be null");
        assertNotNull(client, () -> "Client cannot be null");
        assertEquals(expectedPhone, contact.getPhone(), () -> "Phone not matches");
        assertEquals(expectedClient, contact.getClient(), () -> "Client not matches");
        assertEquals(expectedClient, contact.getClient(), () -> "Client not matches");
        assertEquals(expectedClientName, client.getFullName(), () -> "Client name not matches");
    }
}