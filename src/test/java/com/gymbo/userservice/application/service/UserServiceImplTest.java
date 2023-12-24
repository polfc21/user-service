package com.gymbo.userservice.application.service;

import com.gymbo.userservice.domain.dao.UserDao;
import com.gymbo.userservice.domain.exception.ExistingUserException;
import com.gymbo.userservice.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void givenNonExistentUsernameWhenRegisterUserThenSaveUser() {
        User user = mock(User.class);
        String encryptedPassword = "encryptedPassword";

        when(user.getUsername()).thenReturn("username");
        when(this.userDao.findByUsername(user.getUsername())).thenReturn(null);
        when(user.getPassword()).thenReturn("password");
        when(this.passwordEncoder.encode(user.getPassword())).thenReturn(encryptedPassword);
        doNothing().when(this.userDao).save(user);
        this.userService.register(user);

        verify(user).setPassword(encryptedPassword);
        verify(this.userDao).save(user);
    }

    @Test
    void givenExistentUsernameWhenCreateUserThenThrowExistingUserException() {
        User existingUser = mock(User.class);

        when(existingUser.getUsername()).thenReturn("username");
        when(this.userDao.findByUsername(existingUser.getUsername())).thenReturn(existingUser);

        assertThrows(ExistingUserException.class,() -> this.userService.register(existingUser));
    }

}
