package io.github.williamandradesantana.nubank_challenge.domain.client;

import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() {
        // Given - Arrange
        client = new Client(1L, "full-name-test", new HashSet<>());
    }

    @AfterEach
    void afterEach() {
        client = null;
    }

    @Test
    @DisplayName("Test: when create a client should return all attributes corrects")
    void test_When_ClientIsCreated_Should_ReturnAllAttributesCorrects() {
        // Given
        Long expectedId = 1L;
        String expectedFullName = "full-name-test";
        Set<Contact> expectedContacts = new HashSet<>();

        // Then
        assertEquals(expectedId, client.getId());
        assertEquals(expectedFullName, client.getFullName());
        assertEquals(expectedContacts, client.getContacts());
    }

    @Test
    @DisplayName("Test: when create a client with contact should return all attributes corrects")
    void test_When_ClientIsCreatedWithContact_Should_ReturnCorrects() {
        Long expectedId = 1L;
        String expectedFullName = "full-name-test";
        String expectedPhone = "12346667789";

        Contact contact = new Contact(expectedPhone, client);
        Set<Contact> expectedContacts = new HashSet<>();

        // When - Act
        expectedContacts.add(contact);
        client.addContact(contact);

        // Then - Assert
        assertEquals(expectedId, client.getId(), () -> "Expected client id not matches");
        assertEquals(expectedFullName, client.getFullName(), () -> "Expected client fullName not matches");
        assertEquals(expectedContacts, client.getContacts(), () -> "Expected client contacts not matches");
        assertTrue(client.getContacts().contains(contact), () -> "Expected client contact not matches");
        assertEquals(expectedContacts.size(), client.getContacts().size(), () -> "Expected quantity contacts not matches");
    }

    @Test
    @DisplayName("Test: when create a client with contact should return all attributes corrects")
    void test_When_ContactIsRemoved_ShouldRemoveContactFromClient() {
        Long expectedId = 1L;
        String expectedFullName = "full-name-test";
        String expectedPhone = "12346667789";

        Contact contact = new Contact(expectedPhone, client);
        Set<Contact> expectedContacts = new HashSet<>();

        // When - Act
        expectedContacts.add(contact);
        client.addContact(contact);
        expectedContacts.remove(contact);
        client.removeContact(contact);

        // Then - Assert
        assertEquals(expectedId, client.getId(), () -> "Expected client id not matches");
        assertEquals(expectedFullName, client.getFullName(), () -> "Expected client fullName not matches");
        assertEquals(expectedContacts, client.getContacts(), () -> "Expected client contacts not matches");
        assertTrue(client.getContacts().isEmpty(), () -> "Expected contacts to be empty");
    }
}