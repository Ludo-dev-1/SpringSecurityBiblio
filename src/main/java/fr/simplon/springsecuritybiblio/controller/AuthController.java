package fr.simplon.springsecuritybiblio.controller;

import fr.simplon.springsecuritybiblio.model.Users;
import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final UsersRepository usersRepositoryInjected;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {

        if (usersRepositoryInjected.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cet utilisateur existe déjà"
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepositoryInjected.save(user);
    }

}
