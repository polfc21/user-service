package com.gymbo.userservice.application.service;

import com.gymbo.userservice.domain.dao.UserDao;
import com.gymbo.userservice.domain.exception.ExistingUserException;
import com.gymbo.userservice.domain.model.User;
import com.gymbo.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        User existingUser = this.userDao.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ExistingUserException("Username already exists");
        }
        this.encodePassword(user);
        this.userDao.save(user);
    }

    private void encodePassword(User user) {
        String password = user.getPassword();
        String encodedPassword = this.passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
    }

}
