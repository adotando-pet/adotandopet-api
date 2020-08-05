package com.pet.adoptapi.repository;

import com.pet.adoptapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
