package io.github.williamandradesantana.nubank_challenge.validators;

import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.RequiredObjectIsNullException;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {

    public void validate(ClientRequest request) {
        if (request == null) throw new RequiredObjectIsNullException();
        if (request.fullName() == null || request.fullName().isBlank() || request.fullName().length() > 100)
            throw new BusinessException("Full name must be between 1 and 100 characters.");
    }
}
