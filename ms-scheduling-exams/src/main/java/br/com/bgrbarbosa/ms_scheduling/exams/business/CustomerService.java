package br.com.bgrbarbosa.ms_scheduling.exams.business;


import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer insert(Customer entity);

    List<Customer> findAll(Pageable page);

    Customer findById(UUID uuid);

    void delete(UUID uuid);

    Customer update(Customer entity);
}
