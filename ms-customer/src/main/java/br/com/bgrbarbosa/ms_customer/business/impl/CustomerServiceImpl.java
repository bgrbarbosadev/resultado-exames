package br.com.bgrbarbosa.ms_customer.business.impl;

import br.com.bgrbarbosa.ms_customer.business.CustomerService;
import br.com.bgrbarbosa.ms_customer.business.producer.PublishedCustomerService;
import br.com.bgrbarbosa.ms_customer.config.Messages;
import br.com.bgrbarbosa.ms_customer.infraestruture.entity.Customer;
import br.com.bgrbarbosa.ms_customer.infraestruture.exceptions.DatabaseException;
import br.com.bgrbarbosa.ms_customer.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_customer.infraestruture.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final PublishedCustomerService producerCustomerService;

    private final RestTemplate restTemplate;

    private final String URL_CUSTOMER_SCHEDLING = "http://ms-scheduling/scheduling/customer";

    @Override
    public Customer insert(Customer entity) throws Exception {
        Customer result = new Customer();
        try {
            result = repository.save(entity);
            producerCustomerService.createCustomer(result);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + result.getCodCustomer());
        }
        return result;
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

//    @Override
//    public void delete(UUID uuid) {
//        if (!repository.existsById(uuid)) {
//            throw new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND);
//        }
//        repository.deleteById(uuid);
//    }

    @Override
    public void delete(UUID uuid) throws Exception {
        Customer customer = repository.findById(uuid).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        if (existCustomerByScheduling(uuid)) {
            throw new DatabaseException(Messages.EXISTING_EXAM);
        }

        try {
            repository.deleteById(customer.getCodCustomer());
            producerCustomerService.deleteCustomer(customer);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + customer.getCodCustomer());
        }

    }

    @Override
    public Customer update(Customer entity) throws Exception {
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
        Customer result = new Customer();
        try {
            result = repository.save(aux);
            producerCustomerService.updateCustomer(result);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + result.getCodCustomer());
        }
        return result;
    }

    public Boolean existCustomerByScheduling(UUID uuid){
        boolean exists = false;
        try {
            String urlDelecao = URL_CUSTOMER_SCHEDLING + "/" + uuid;
            Boolean isCustomerShceduling = restTemplate.getForObject(urlDelecao, Boolean.class);
            exists = (isCustomerShceduling != null && isCustomerShceduling) ? true : false;

        } catch (Exception e) {
            new Exception(e.getMessage());
        }

        return exists;
    }
}
