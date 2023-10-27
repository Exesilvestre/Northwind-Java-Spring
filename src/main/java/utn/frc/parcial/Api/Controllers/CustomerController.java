package utn.frc.parcial.Api.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Services.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findCustomers() {
        List<CustomerResponseDTO> customers = customerService.findAll();

        return customers.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomer(@PathVariable String id) {
        Optional<?> customer = customerService.findById(id);

        return customer.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customer.get());
    }


    @PostMapping
    public ResponseEntity<CustomerResponseDTO> addCustomer(@RequestBody @Valid CreateCustomerDTO customer) {
        return new ResponseEntity<>(customerService.add(customer), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody UpdateCustomerDTO customer
    ) {
        return new ResponseEntity<>(customerService.update(id, customer), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable String id) {
        return customerService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }




}
