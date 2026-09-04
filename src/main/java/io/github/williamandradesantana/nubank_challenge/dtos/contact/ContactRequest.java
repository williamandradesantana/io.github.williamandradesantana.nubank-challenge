package io.github.williamandradesantana.nubank_challenge.dtos.contact;

import lombok.Builder;

@Builder
public record ContactRequest(String phone, Long clientId) {}
