package io.github.williamandradesantana.nubank_challenge.mappers;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactResponse;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toDomain(ContactRequest request, Client client) {
        return new Contact(request.phone(), client);
    }

    public ContactResponse toResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .phone(contact.getPhone())
                .clientId(contact.getClient().getId())
                .build();
    }
}
