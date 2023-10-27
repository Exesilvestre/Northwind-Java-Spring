package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Repositories.CustomersRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    private CustomersRepository customersRepository;

    public CustomerService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerResponseDTO> findAll() {
        return customersRepository.findAll().stream().map(customer -> new CustomerResponseDTO(customer)).toList();
    }


    public Optional<CustomerResponseDTO> findById(String  customerId) {
        Optional<Customer> customer = customersRepository.findById(customerId);

        return customer.isEmpty()
                ? Optional.empty()
                : Optional.of(new CustomerResponseDTO(customer.get()));
    }


    public CustomerResponseDTO add(CreateCustomerDTO createCustomerDTO) {
        Customer customer = new Customer(createCustomerDTO);
        return new CustomerResponseDTO(customersRepository.save(customer));
    }

    public CustomerResponseDTO update(String customerId, UpdateCustomerDTO updateCustomerDTO) {
        Customer customer = customersRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Customer %s inexistente", customerId))
        );

        return new CustomerResponseDTO(customersRepository.save(customer.update(updateCustomerDTO)));
    }


    public boolean deleteById(String customerId) {
        if (!customersRepository.existsById(customerId)) {
            return false;
        }

        customersRepository.deleteById(customerId);
        return true;
    }
}
