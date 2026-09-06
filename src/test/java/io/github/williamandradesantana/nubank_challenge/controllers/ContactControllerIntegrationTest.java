package io.github.williamandradesantana.nubank_challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.williamandradesantana.nubank_challenge.PostgresIntegrationTest;
import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerIntegrationTest extends PostgresIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("client-test");
    }


    @Test
    @DisplayName("Test: create a contact via HTTP and retrieve a client with contacts")
    void test_CreateAContactViaHttpAndRetrieveAClientWithContacts() throws Exception {
        clientRepository.save(client);
        ContactRequest request = new ContactRequest("119999998", client.getId());

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/clientes/{id}/contatos", client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts.length()").value(1))
                .andExpect(jsonPath("$.contacts[0].phone").value(request.phone()));
    }
}
