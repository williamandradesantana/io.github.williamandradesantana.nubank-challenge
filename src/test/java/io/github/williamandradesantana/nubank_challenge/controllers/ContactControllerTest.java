package io.github.williamandradesantana.nubank_challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ContactRequest contactRequest;

    @BeforeEach
    void setUp() {
        contactRequest = new ContactRequest("111111", 1L);
    }

    @Test
    @DisplayName("Test: post /contatos must be return 201 when request is valid")
    void test_() throws Exception {
        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactRequest))
            ).andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Test: post /contatos must be return 404 when request client id not exists")
    void test() throws Exception {
        ContactRequest contactRequestWithoutClientId = new ContactRequest(contactRequest.phone(), 99L);

        doThrow(new ResourceNotFoundException("Client not found: 99"))
                .when(contactService)
                .createContact(contactRequestWithoutClientId);

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactRequestWithoutClientId))
            ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test: post /contatos must be return 409 when phone already exists!")
    void test1() throws Exception {
        doThrow(new BusinessException("This phone is already associated with a number"))
                .when(contactService)
                .createContact(contactRequest);

        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactRequest))
            ).andExpect(status().isConflict());
    }
}