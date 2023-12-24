package com.gymbo.userservice.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymbo.userservice.domain.exception.ExistingUserException;
import com.gymbo.userservice.domain.model.User;
import com.gymbo.userservice.domain.service.UserService;
import com.gymbo.userservice.infrastructure.controller.constant.ApiConstant;
import com.gymbo.userservice.infrastructure.controller.dto.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenCorrectUserRequestWhenCreateUserThenReturnUserCreatedAnd201() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .email("email@email.com")
                .birthDate(LocalDate.of(1997, 2, 17))
                .password("password")
                .build();
        User user = mock(User.class);

        when(this.mapper.map(userRequest, User.class)).thenReturn(user);
        doNothing().when(this.userService).register(user);
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API + ApiConstant.V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    void givenIncorrectUserRequestWhenCreateUserThenReturn400() throws Exception {
        UserRequest incorrectUserRequest = UserRequest.builder().build();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API + ApiConstant.V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectUserRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenExistentUsernameWhenCreateUserThenReturn409() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .email("email@email.com")
                .birthDate(LocalDate.of(1997, 2, 17))
                .password("password")
                .build();
        User user = mock(User.class);

        when(this.mapper.map(userRequest, User.class)).thenReturn(user);
        doThrow(ExistingUserException.class).when(this.userService).register(user);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API + ApiConstant.V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CONFLICT.value()));
    }

}