package com.plantcontroller.server.entities;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Tag(name="User")
public class User {
    private @Id @GeneratedValue int id;
    private String name;
    private String email;
    private String password;
}
