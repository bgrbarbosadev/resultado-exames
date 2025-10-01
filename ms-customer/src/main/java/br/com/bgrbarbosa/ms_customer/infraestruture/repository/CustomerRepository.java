package br.com.bgrbarbosa.ms_customer.infraestruture.repository;

import br.com.bgrbarbosa.ms_customer.infraestruture.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
