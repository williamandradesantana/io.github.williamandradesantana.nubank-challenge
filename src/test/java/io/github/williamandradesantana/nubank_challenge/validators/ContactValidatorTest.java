package io.github.williamandradesantana.nubank_challenge.validators;

import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.nubank_challenge.repositories.ContactRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ContactValidatorTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactValidator contactValidator;

    @Test
    @DisplayName("Should throw RequiredObjectIsNullException when request is null")
    void test_WhenRequestIsNull_ShouldThrowRequiredObjectIsNullException() {
        String expectedErrorMessage = "It is not allowed to persist a null object!";

        RequiredObjectIsNullException exception =
                assertThrows(RequiredObjectIsNullException.class, () -> contactValidator.validate(null));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when client ID is null")
    void test_WhenClientIdIsNull_ShouldThrowBusinessException() {
        String expectedErrorMessage = "Client id is required";
        ContactRequest request = new ContactRequest("1323121", null);

        BusinessException exception = assertThrows(BusinessException.class, () -> contactValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone is null")
    void test_WhenPhoneIsNull_ShouldThrowBusinessException() {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        ContactRequest request = new ContactRequest(null, 1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> contactValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone is empty")
    void test_WhenPhoneIsEmpty_ShouldThrowBusinessException() {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        ContactRequest request = new ContactRequest("", 1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> contactValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone has more than 20 characters")
    void test_WhenPhoneHasMoreThan20Characters_ShouldThrowBusinessException()  {
        String expectedErrorMessage = "Phone must be between 1 and 20 characters.";
        ContactRequest request = new ContactRequest("31321231231231231231231231231231231231", 1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> contactValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}