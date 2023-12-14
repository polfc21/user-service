package com.gymbo.userservice.infrastructure.repository;

import com.gymbo.userservice.infrastructure.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenUsernameWhenFindByUsernameThenReturnUser() {
        String username = "username";
        UserEntity entity = UserEntity.builder()
                .username(username)
                .build();

        this.entityManager.persist(entity);

        assertThat(this.userRepository.findByUsername(username).getUsername(), is(username));
    }

    @Test
    void givenNullUsernameWhenFindByUsernameThenReturnNull() {
        assertNull(this.userRepository.findByUsername("null"));
    }

}
