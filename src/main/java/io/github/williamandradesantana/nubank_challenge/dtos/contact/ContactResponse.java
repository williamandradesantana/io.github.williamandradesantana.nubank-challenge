package io.github.williamandradesantana.nubank_challenge.dtos.contact;

import lombok.Builder;

@Builder
public record ContactResponse(Long id, String phone, Long clientId) {}
