package fr.simplon.springsecuritybiblio.service;

import fr.simplon.springsecuritybiblio.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepositoryInjected;

    public UsersService(UsersRepository usersRepositoryInjected) {
        this.usersRepositoryInjected = usersRepositoryInjected;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.usersRepositoryInjected.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + email));

    }
}
