package fr.simplon.springsecuritybiblio.controller;

import fr.simplon.springsecuritybiblio.dto.LoginDto;
import fr.simplon.springsecuritybiblio.dto.LoginRequestDto;
import fr.simplon.springsecuritybiblio.model.Users;
import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import fr.simplon.springsecuritybiblio.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authManager;
    private final TokenService tokenService;


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

    @PostMapping("/login")
    public LoginDto login(@RequestBody LoginRequestDto dto) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        String token = tokenService.generateToken(auth);

        Users userConnected = (Users) auth.getPrincipal();

        return new LoginDto(
                token,
                userConnected.getEmail()
        );
    }

}
