package com.gymbo.userservice.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "player")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
}

