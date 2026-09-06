package io.github.williamandradesantana.nubank_challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientResponse;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.nubank_challenge.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Test: GET /clientes must be return 200 with list of clients")
    void test_MustBeReturn200WithListOfClients() throws Exception {
        ClientResponse response = new ClientResponse(1L, "full-name-test", new HashSet<>());
        when(clientService.getAllClientsWithContacts()).thenReturn(List.of(response));

        mockMvc.perform(get("/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(response.id()))
            .andExpect(jsonPath("$[0].fullName").value(response.fullName()));
    }

    @Test
    @DisplayName("Test: GET /clientes/{id}/contatos must be return 200 when client exists")
    void test_MustBeReturn200WhenClientExists() throws Exception {
        ClientResponse response = new ClientResponse(1L, "full-name-test", new HashSet<>());
        when(clientService.retrieveOneClientWithContacts(response.id())).thenReturn(response);

        mockMvc.perform(get("/clientes/{id}/contatos", response.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.fullName").value(response.fullName()));
    }

    @Test
    @DisplayName("Test: GET /clientes/{id}/contatos must be return 404 when client not exists")
    void test_MustBeReturn404WhenClientNotExists() throws Exception {
        ClientResponse response = new ClientResponse(99L, "full-name-test", new HashSet<>());
        String expectedErrorMessage = "Client not found: 99";
        when(clientService.retrieveOneClientWithContacts(response.id()))
                .thenThrow(new ResourceNotFoundException(expectedErrorMessage));

        mockMvc.perform(get("/clientes/{id}/contatos", response.id()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    @Test
    @DisplayName("Test: POST /clientes must be return 201 when request is valid")
    void test_MustBeReturn201WhenRequestIsValid() throws Exception {
        ClientRequest request = new ClientRequest("full-name-test");
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test: POST /clientes must be return 409 when request have fullName invalid")
    void test_MustBeReturn409WhenRequestHaveFullNameInvalid() throws Exception {
        ClientRequest request = new ClientRequest("");
        String expectedErrorMessage = "Full name must be between 1 and 100 characters.";
        doThrow(new BusinessException(expectedErrorMessage))
                .when(clientService).createClient(request);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }
}