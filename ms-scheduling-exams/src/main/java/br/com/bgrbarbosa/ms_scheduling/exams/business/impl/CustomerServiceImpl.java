package br.com.bgrbarbosa.ms_scheduling.exams.business.impl;


import br.com.bgrbarbosa.ms_scheduling.exams.business.CustomerService;
import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Customer insert(Customer entity) {
        return repository.save(entity);
    }

    @Override
    public List<Customer> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public Customer findById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
    }

    @Override
    public void delete(UUID uuid) {
        if (!repository.existsById(uuid)) {
            throw new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND);
        }
        repository.deleteById(uuid);
    }

    @Override
    public Customer update(Customer entity) {
        Customer aux = repository.findById(entity.getCodCustomer()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        aux.setName(entity.getName());
        aux.setCpf(entity.getCpf());
        aux.setEmail(entity.getEmail());
        aux.setTel(entity.getTel());
        aux.setCel(entity.getCel());
        aux.setAddress(entity.getAddress());
        aux.setNeighborhood(entity.getNeighborhood());
        aux.setCity(entity.getCity());
        aux.setState(entity.getState());
        aux.setCep(entity.getCep());
        return repository.save(aux);
    }
}
