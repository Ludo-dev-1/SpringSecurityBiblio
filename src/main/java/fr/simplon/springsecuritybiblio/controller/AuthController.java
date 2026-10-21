package fr.simplon.springsecuritybiblio.controller;

import fr.simplon.springsecuritybiblio.dto.LoginDto;
import fr.simplon.springsecuritybiblio.dto.LoginRequestDto;
import fr.simplon.springsecuritybiblio.dto.RegisterDto;
import fr.simplon.springsecuritybiblio.model.RoleEntity;
import fr.simplon.springsecuritybiblio.model.Users;
import fr.simplon.springsecuritybiblio.repository.RoleRepository;
import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import fr.simplon.springsecuritybiblio.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final UsersRepository usersRepositoryInjected;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterDto dto) {

        if (usersRepositoryInjected.existsByEmail(dto.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cet utilisateur existe déjà"
            );
        }

        RoleEntity userRole = roleRepository.findById("ROLE_USER")
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Le rôle ROLE_USER n'existe pas"
                        ));

        Users user = new Users();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        // Attribution du rôle USER par défaut
        user.setAuthorities(Set.of(userRole));

        usersRepositoryInjected.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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

        String role = userConnected.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow()
                .getAuthority();

        return new LoginDto(
                token,
                userConnected.getEmail(),
                role
        );
    }

}
