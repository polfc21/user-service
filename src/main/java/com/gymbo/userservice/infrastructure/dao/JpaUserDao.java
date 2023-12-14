package com.gymbo.userservice.infrastructure.dao;

import com.gymbo.userservice.domain.dao.UserDao;
import com.gymbo.userservice.domain.model.User;
import com.gymbo.userservice.infrastructure.repository.UserRepository;
import com.gymbo.userservice.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUserDao implements UserDao {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public User findByUsername(String username) {
        UserEntity entity = this.userRepository.findByUsername(username);
        return entity != null ? this.mapper.map(entity, User.class) : null;
    }

    @Override
    public void save(User user) {
        UserEntity entity = this.mapper.map(user, UserEntity.class);
        this.userRepository.save(entity);
    }

}
