package br.com.bgrbarbosa.ms_user.business.impl;

import br.com.bgrbarbosa.ms_user.business.UserService;
import br.com.bgrbarbosa.ms_user.config.Messages;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.User;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.UserException;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.UsernameNotFoundException;
import br.com.bgrbarbosa.ms_user.infraestruture.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User aux = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_EXISTS + ": " + email));
        return aux;
    }

    @Override
    public User insert(User user) throws UserException {
        User aux = user;
        if (repository.existsByEmail(aux.getEmail())) {
            throw new UserException(Messages.Existing_User);
        }
        aux.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public List<User> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND + id)
        );
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND + id);
        }
        repository.deleteById(id);
    }

    @Override
    public User update(User user) {
        User aux = repository.findById(user.getIdUser()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        aux.setFirstName(user.getFirstName());
        aux.setLastName(user.getLastName());
        aux.setEmail(user.getEmail());
        aux.setPassword(passwordEncoder.encode(user.getPassword()));
        aux.setRoles(user.getRoles());
        return repository.save(aux);
    }

}
