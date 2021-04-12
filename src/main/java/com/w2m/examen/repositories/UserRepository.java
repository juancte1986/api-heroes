package com.w2m.examen.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w2m.examen.models.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByUsernameIgnoreCaseAndPassword(String username, String password);
}
