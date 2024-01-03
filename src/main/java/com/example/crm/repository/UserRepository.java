package com.example.crm.repository;


import java.util.List;
import java.util.Optional;

import com.example.crm.model.ERole;
import com.example.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByPhone(String username);

    Boolean existsByEmail(String email);


    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoles(@Param("roleName") ERole roleName);}
