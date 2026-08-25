package fr.simplon.springsecuritybiblio.service;

import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepositoryinjected;

    public UsersService(UsersRepository usersRepositoryinjected) {
        this.usersRepositoryinjected = usersRepositoryinjected;
    }
}
