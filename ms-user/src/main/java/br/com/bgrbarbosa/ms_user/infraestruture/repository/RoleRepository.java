package br.com.bgrbarbosa.ms_user.infraestruture.repository;

import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
