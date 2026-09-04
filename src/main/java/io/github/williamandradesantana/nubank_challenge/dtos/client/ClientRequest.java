package io.github.williamandradesantana.nubank_challenge.dtos.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ClientRequest(
    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    String fullName
) {}