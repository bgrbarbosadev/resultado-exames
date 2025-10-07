package br.com.bgrbarbosa.ms_customer.business;

import br.com.bgrbarbosa.ms_customer.infraestruture.entity.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer insert(Customer entity) throws Exception;

    List<Customer> findAll(Pageable page);

    Customer findById(UUID uuid);

    void delete(UUID uuid) throws Exception;

    Customer update(Customer entity) throws Exception;
}
