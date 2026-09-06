package io.github.williamandradesantana.nubank_challenge.validators;

import io.github.williamandradesantana.nubank_challenge.dtos.client.ClientRequest;
import io.github.williamandradesantana.nubank_challenge.dtos.contact.ContactRequest;
import io.github.williamandradesantana.nubank_challenge.exceptions.BusinessException;
import io.github.williamandradesantana.nubank_challenge.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.nubank_challenge.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientValidatorTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientValidator clientValidator;

    @Test
    @DisplayName("Should throw RequiredObjectIsNullException when request is null")
    void test_WhenRequestIsNull_ShouldThrowRequiredObjectIsNullException() {
        String expectedErrorMessage = "It is not allowed to persist a null object!";

        RequiredObjectIsNullException exception =
                assertThrows(RequiredObjectIsNullException.class, () -> clientValidator.validate(null));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone is null")
    void test_WhenPhoneIsNull_ShouldThrowBusinessException() {
        String expectedErrorMessage = "Full name must be between 1 and 100 characters.";
        ClientRequest request = new ClientRequest(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> clientValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone is empty")
    void test_WhenPhoneIsEmpty_ShouldThrowBusinessException() {
        String expectedErrorMessage = "Full name must be between 1 and 100 characters.";
        ClientRequest request = new ClientRequest("");

        BusinessException exception = assertThrows(BusinessException.class, () -> clientValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw BusinessException when phone has more than 20 characters")
    void test_WhenPhoneHasMoreThan20Characters_ShouldThrowBusinessException()  {
        String expectedErrorMessage = "Full name must be between 1 and 100 characters.";
        ClientRequest request =
            new ClientRequest(
            "3132123123123123123123123123123" +
                    "1231231321312312312312312313312313121231321231231231231231231231231231"
            );

        BusinessException exception = assertThrows(BusinessException.class, () -> clientValidator.validate(request));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

}