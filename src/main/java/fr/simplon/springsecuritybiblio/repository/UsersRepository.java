package fr.simplon.springsecuritybiblio.repository;

import fr.simplon.springsecuritybiblio.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

}
