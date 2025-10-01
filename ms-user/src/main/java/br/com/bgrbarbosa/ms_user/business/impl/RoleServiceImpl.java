package br.com.bgrbarbosa.ms_user.business.impl;

import br.com.bgrbarbosa.ms_user.business.RoleService;
import br.com.bgrbarbosa.ms_user.config.Messages;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_user.infraestruture.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role insert(Role role) {
        return repository.save(role);
    }

    @Override
    public List<Role> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    @Override
    public Role update(Role role) {
        Role aux = repository.findById(role.getIdRole()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        aux.setRole(role.getRole());
        return repository.save(aux);
    }
}
