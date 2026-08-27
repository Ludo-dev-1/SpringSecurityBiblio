package fr.simplon.springsecuritybiblio.model;


import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Setter
@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {
    @Id
    private String authority;

    public RoleEntity() {
    }

    @Override
    public String getAuthority() {
        // permet à Spring Security de connaître le nom correspondant au role
        return this.authority;
    }

}
