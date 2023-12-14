package com.gymbo.userservice.infrastructure.controller;

import com.gymbo.userservice.domain.model.User;
import com.gymbo.userservice.domain.service.UserService;
import com.gymbo.userservice.infrastructure.controller.constant.ApiConstant;
import com.gymbo.userservice.infrastructure.controller.dto.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.API + ApiConstant.V1 + ApiConstant.USERS)
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequest request) {
        User user = this.mapper.map(request, User.class);
        this.userService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
