package br.com.bgrbarbosa.ms_user.business;

import br.com.bgrbarbosa.ms_user.infraestruture.entity.User;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.UserException;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.UsernameNotFoundException;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    User insert(User user) throws UserException;

    List<User> findAll(Pageable page);

    User findById(Long id);

    void delete(Long id);

    User update(User user);

    User loadUserByUsername(String email) throws UsernameNotFoundException;
}
