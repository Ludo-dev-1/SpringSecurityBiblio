package fr.simplon.springsecuritybiblio.init;

import fr.simplon.springsecuritybiblio.model.RoleEntity;
import fr.simplon.springsecuritybiblio.model.Users;
import fr.simplon.springsecuritybiblio.repository.RoleRepository;
import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            RoleRepository roleRepository,
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        RoleEntity roleUser = roleRepository.findById("ROLE_USER")
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setAuthority("ROLE_USER");
                    return roleRepository.save(role);
                });

        RoleEntity roleAdmin = roleRepository.findById("ROLE_ADMIN")
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setAuthority("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        if (!usersRepository.existsByEmail("admin@admin.fr")) {

            Users admin = new Users();
            admin.setName("Administrateur");
            admin.setEmail("admin@admin.fr");
            admin.setPassword(passwordEncoder.encode("admin"));

            admin.setAuthorities(
                    Set.of(roleUser, roleAdmin)
            );

            usersRepository.save(admin);
        }
    }
}