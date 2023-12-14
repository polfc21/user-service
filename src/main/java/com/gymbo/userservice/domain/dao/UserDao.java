package com.gymbo.userservice.domain.dao;

import com.gymbo.userservice.domain.model.User;

public interface UserDao {
    User findByUsername(String username);
    void save(User user);
}
