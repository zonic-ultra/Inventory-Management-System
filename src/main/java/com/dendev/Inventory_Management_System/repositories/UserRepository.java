package com.dendev.Inventory_Management_System.repositories;

import com.dendev.Inventory_Management_System.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
