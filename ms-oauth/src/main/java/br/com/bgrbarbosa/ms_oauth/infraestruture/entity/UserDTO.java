package br.com.bgrbarbosa.ms_oauth.infraestruture.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(
        Long idUser,
        String firstName,
        String lastName,
        String email,
        String password,
        Set<RoleDTO> roles
) {
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.role()))
                .collect(Collectors.toList());
    }
}
