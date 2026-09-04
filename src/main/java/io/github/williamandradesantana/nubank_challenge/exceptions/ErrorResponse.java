package io.github.williamandradesantana.nubank_challenge.exceptions;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorResponse(Instant timestamp, String message, String details) {
}
