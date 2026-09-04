package io.github.williamandradesantana.nubank_challenge.dtos.client;

import lombok.Builder;

@Builder
public record ClientRequest(String fullName) {}