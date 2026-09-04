package io.github.williamandradesantana.nubank_challenge.mappers;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientResponse;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public Client toDomain(ClientRequest request) {
        return new Client(request.fullName());
    }

    public ClientResponse toResponse(Client client) {
        Set<ContactResponse> contacts = client.getContacts().stream().map(
            contact -> ContactResponse.builder()
                    .id(contact.getId())
                    .phone(contact.getPhone())
                    .clientId(client.getId())
                    .build()
        ).collect(Collectors.toSet());
        return new ClientResponse(client.getId(), client.getFullName(), contacts);
    }
}
