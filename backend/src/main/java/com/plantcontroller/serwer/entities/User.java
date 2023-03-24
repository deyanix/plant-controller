package com.plantcontroller.serwer.entities;

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
public class User {
    private @Id @GeneratedValue int Id;
    private String userName;
    private String userEmail;
    private String userPassword;
}
