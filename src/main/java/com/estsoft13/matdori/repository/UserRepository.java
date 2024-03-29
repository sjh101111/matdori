package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndEmail(String username, String email);
}