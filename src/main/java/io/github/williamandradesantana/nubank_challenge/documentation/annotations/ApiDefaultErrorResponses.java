package io.github.williamandradesantana.nubank_challenge.documentation.annotations;

import io.github.williamandradesantana.nubank_challenge.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface ApiDefaultErrorResponses { }
