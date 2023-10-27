package utn.frc.parcial.Api;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.frc.parcial.Api.Controllers.CustomerController;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Repositories.CustomersRepository;
import utn.frc.parcial.Services.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class CustomerApiTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;
    @Mock
    CustomersRepository customersRepository;

    private final Customer CUSTOMER = new Customer("EXEQ","Arcor", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");


    @Test
    public void getAllSucces() {
        // Arrange
        List<CustomerResponseDTO> customers = new ArrayList<>();
        customers.add(new CustomerResponseDTO(CUSTOMER));

        Mockito.when(customerService.findAll()).thenReturn(customers);

        // Act
        ResponseEntity<List<CustomerResponseDTO>> responseEntity = customerController.findCustomers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<CustomerResponseDTO> actualCustomers = responseEntity.getBody();
        assertEquals(customers, actualCustomers);
    }

    @Test
    public void getAllEmpty() {
        // Arrange
        List<CustomerResponseDTO> customers = new ArrayList<>();
        Mockito.when(customerService.findAll()).thenReturn(customers);

        // Act
        ResponseEntity<List<CustomerResponseDTO>> responseEntity = customerController.findCustomers();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verifica si el cuerpo de la respuesta es null o una lista vacía
        assertNull(responseEntity.getBody());
    }

    @Test
    public void findByIdSucces(){


        CustomerResponseDTO customer = new CustomerResponseDTO(CUSTOMER);

        Mockito.when(customerService.findById(anyString())).thenReturn(Optional.of(customer));

        ResponseEntity responseEntity = customerController.findCustomer("EXEQ");
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void findbyIDAndnotExiste(){
        Mockito.when(customerService.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity responseEntity = customerController.findCustomer(anyString());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verifica si el cuerpo de la respuesta es null o una lista vacía
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

    }

    @Test
    public void addProductSucces(){

        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO("EXEQ","Arcor", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(CUSTOMER);
        // Configurar el comportamiento del servicio mock
        Mockito.when(customerService.add(Mockito.any(CreateCustomerDTO.class))).thenReturn(customerResponseDTO);


        // Llamar al método add del controler
        ResponseEntity responseEntity = customerController.addCustomer(createCustomerDTO);

        // Crear un objeto CategoriesResponseDTO manualmente para comparar
        CustomerResponseDTO customerResponseDTOesperado = new CustomerResponseDTO(CUSTOMER);

        // Verificar que el resultado obtenido es el esperado
        assertEquals(customerResponseDTOesperado, customerResponseDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteByIdExisting() {
        String existingCustomerId = "EXEQ";

        Mockito.when(customerService.deleteById(existingCustomerId)).thenReturn(true);

        // Actuar: llamar al método deleteById
        ResponseEntity responseEntity = customerController.deleteCustomer(existingCustomerId);

        // Verificar que se haya llamado al método deleteById del repositorio con el ID correcto
        Mockito.verify(customerService, Mockito.times(1)).deleteById(existingCustomerId);

        //tira 204 cuando se elimino correctamente
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteByIdNonExisting() {
        String nonExistingCustomerId = "EXEQ";

        Mockito.when(customerService.deleteById(nonExistingCustomerId)).thenReturn(false);

        // Actuar: llamar al método deleteById
        ResponseEntity responseEntity = customerController.deleteCustomer(nonExistingCustomerId);

        // Verificar que se haya llamado al método deleteById del repositorio con el ID correcto
        Mockito.verify(customerService, Mockito.times(1)).deleteById(nonExistingCustomerId);

        //tira 204 cuando se elimino correctamente
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }



}
