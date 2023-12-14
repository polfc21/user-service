package com.gymbo.userservice.application.service;

import com.gymbo.userservice.domain.dao.UserDao;
import com.gymbo.userservice.domain.exception.ExistingUserException;
import com.gymbo.userservice.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Test
    void givenNonExistentUsernameWhenRegisterUserThenSaveUser() {
        User user = mock(User.class);

        when(user.getUsername()).thenReturn("username");
        when(this.userDao.findByUsername(user.getUsername())).thenReturn(null);
        doNothing().when(this.userDao).save(user);
        this.userService.register(user);

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
