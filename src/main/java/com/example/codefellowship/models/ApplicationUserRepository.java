package com.example.codefellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository {
    public ApplicationUser findByUsername(String username);
}
