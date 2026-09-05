package io.github.williamandradesantana.nubank_challenge.controllers;

import io.github.williamandradesantana.nubank_challenge.documentation.annotations.ApiController;
import io.github.williamandradesantana.nubank_challenge.documentation.annotations.ApiDefaultErrorResponses;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientResponse;
import io.github.williamandradesantana.nubank_challenge.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@ApiController(tagName = "Clientes", tagDescription = "Gerenciamento de clientes")
public class ClientController {

    private final ClientService clientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar clientes",
        description = "Retorna todos os clientes com seus respectivos contatos."
    )
    public ResponseEntity<List<ClientResponse>> allClientsWithContacts() {
        return ResponseEntity.ok(clientService.getAllClientsWithContacts());
    }

    @GetMapping(value = "{id}/contatos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca um cliente com seus contatos")
    @ApiDefaultErrorResponses
    public ResponseEntity<ClientResponse> retrieveOneClientWithContacts(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.retrieveOneClientWithContacts(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um cliente")
    public ResponseEntity<Void> createClient(@RequestBody ClientRequest request) {
        clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
