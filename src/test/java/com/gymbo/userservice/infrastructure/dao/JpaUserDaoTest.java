package com.gymbo.userservice.infrastructure.dao;

import com.gymbo.userservice.domain.model.User;
import com.gymbo.userservice.infrastructure.repository.UserRepository;
import com.gymbo.userservice.infrastructure.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaUserDaoTest {

    @InjectMocks
    private JpaUserDao jpaUserDao;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void givenUserWhenSaveUserThenSaveUser() {
        User user = mock(User.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(this.mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        this.jpaUserDao.save(user);

        verify(this.userRepository).save(userEntity);
    }

    @Test
    void givenExistentUserWhenFindByUsernameThenReturnUser() {
        User user = mock(User.class);
        UserEntity userEntity = mock(UserEntity.class);
        String username = "username";

        when(user.getUsername()).thenReturn(username);
        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(userEntity);
        when(this.mapper.map(userEntity, User.class)).thenReturn(user);

        assertThat(this.jpaUserDao.findByUsername(username), is(user));
    }

    @Test
    void givenNonExistentUserWhenFindByUsernameThenReturnNull() {
        String nonExistentUsername = "nonExistentUsername";

        when(this.userRepository.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThat(this.jpaUserDao.findByUsername(nonExistentUsername), is(nullValue()));
    }

}