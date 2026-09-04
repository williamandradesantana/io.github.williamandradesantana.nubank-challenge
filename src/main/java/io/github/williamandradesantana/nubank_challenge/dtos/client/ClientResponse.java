package io.github.williamandradesantana.nubank_challenge.dtos.client;

import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactResponse;
import lombok.Builder;

import java.util.Set;

@Builder
public record ClientResponse(Long id, String fullName, Set<ContactResponse> contacts) {
}
