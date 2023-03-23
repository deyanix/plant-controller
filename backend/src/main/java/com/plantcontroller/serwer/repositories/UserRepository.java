package com.plantcontroller.serwer.repositories;

import com.plantcontroller.serwer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
