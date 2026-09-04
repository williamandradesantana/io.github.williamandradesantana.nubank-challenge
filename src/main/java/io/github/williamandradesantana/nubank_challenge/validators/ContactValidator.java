package io.github.williamandradesantana.nubank_challenge.validators;

import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.nubank_challenge.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactValidator {

    private final ContactRepository contactRepository;

    public void validate(ContactRequest request) {
        if (request == null) throw new RequiredObjectIsNullException();
        if (request.phone() == null || request.phone().isBlank() || request.phone().length() > 20)
            throw new BusinessException("Phone must be between 1 and 20 characters.");
        if (phoneAlreadyExists(request.phone()))
            throw new BusinessException("This phone is already associated with a number");
        if (request.clientId() == null) throw new BusinessException("Client id is required");
    }

    private boolean phoneAlreadyExists(String phone) {
        return contactRepository.existsByPhone(phone);
    }
}
