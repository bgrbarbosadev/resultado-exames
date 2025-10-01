package br.com.bgrbarbosa.ms_user.business;


import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    Role insert(Role role);

    List<Role> findAll(Pageable page);

    Role findById(Long id);

    void delete(Long id);

    Role update(Role role);
}
