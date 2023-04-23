package com.plantcontroller.server.repositories;

import com.plantcontroller.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
