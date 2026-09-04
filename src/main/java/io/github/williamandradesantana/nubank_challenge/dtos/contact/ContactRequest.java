package io.github.williamandradesantana.nubank_challenge.dtos.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ContactRequest(
    @Schema(example = "11999998888", description = "Telefone com DDD, somente números")
    String phone,
    Long clientId
) {}
