package com.gymbo.userservice.infrastructure.controller.dto.validator;

import com.gymbo.userservice.infrastructure.controller.dto.request.UserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class UserRequestValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void givenCorrectUserRequestWhenValidateThenViolationsSizeIs0() {
        UserRequest correctUserRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .birthDate(LocalDate.of(1997, 2, 17))
                .email("email@email.com")
                .password("password")
                .build();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(correctUserRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenInvalidEmailWhenValidateThenViolationsSizeIs1() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .birthDate(LocalDate.of(1997, 2, 17))
                .email("email")
                .password("password")
                .build();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenIncorrectUserRequestWhenValidateThenViolationsSizeIs1() {
        UserRequest userRequest = UserRequest.builder().build();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(UserRequest.class.getDeclaredFields().length));
    }

}
