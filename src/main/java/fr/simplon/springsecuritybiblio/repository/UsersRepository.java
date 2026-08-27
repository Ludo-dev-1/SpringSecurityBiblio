package fr.simplon.springsecuritybiblio.repository;

import fr.simplon.springsecuritybiblio.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);

    Optional<Users> findByName(String name);

    Optional<Users> findByEmail(String email);
}