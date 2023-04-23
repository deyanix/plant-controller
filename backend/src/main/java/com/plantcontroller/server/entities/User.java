package com.plantcontroller.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username", length = 50)
    private String username;

    @Column(name="email", length = 100)
    private String email;

    @Column(name="password", length = 30)
    private String password;
}
