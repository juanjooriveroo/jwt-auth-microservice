package com.juanjooriveroo.jwtauthservice.repository;

import com.juanjooriveroo.jwtauthservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.identifier1 = :input OR u.identifier2 = :input")
    Optional<User> findByIdentifier(@Param("input") String input);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.identifier1 = :input OR u.identifier2 = :input")
    boolean existsByIdentifier(@Param("input") String input);
}
