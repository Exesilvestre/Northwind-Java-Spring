package utn.frc.parcial.Services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Entities.Product;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.CustomersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomersRepository customersRepository;
    @InjectMocks
    private CustomerService customerService;

    private final Customer CUSTOMER = new Customer("EXEQ","Arcor", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");

    @Test
    public void getAllSucces(){
        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER);
        Mockito.when(customersRepository.findAll()).thenReturn((customers));
        List<CustomerResponseDTO> customerResponseDTOS = new ArrayList<>();
        customerResponseDTOS = customers.stream().map(p-> new CustomerResponseDTO(p)).toList();

        assertEquals(
                customerResponseDTOS,
                customerService.findAll()
        );
    }

    @Test
    public void getAllEmpty(){
        List<CustomerResponseDTO> customerResponseDTOS = new ArrayList<>();
        Mockito.when(customersRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(customerResponseDTOS, customerService.findAll());
    }

    @Test
    public void findByIdSucces(){


        CustomerResponseDTO customerEsperado = new CustomerResponseDTO(CUSTOMER);

        Mockito.when(customersRepository.findById(anyString())).thenReturn(Optional.of(CUSTOMER));

        CustomerResponseDTO result = customerService.findById("EXEQ").orElse(null);
        assertEquals(customerEsperado, result);
    }

    @Test
    public void findbyIDAndnotExiste(){
        Mockito.when(customersRepository.findById(anyString())).thenReturn(Optional.empty());


        assertEquals(Optional.empty(), customerService.findById(anyString()));
    }

    @Test
    public void updateSucces(){

        UpdateCustomerDTO updateCustomerDTO = new UpdateCustomerDTO("Bagley", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");
        Customer customerCambiado = new Customer("EXEQ","Bagley", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");
        Mockito.when(customersRepository.findById(anyString())).thenReturn(Optional.of(CUSTOMER));

        Mockito.when(customersRepository.save(Mockito.any(Customer.class))).thenReturn(customerCambiado);


        CustomerResponseDTO esperado = new CustomerResponseDTO("EXEQ","Bagley", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233" );

        assertEquals(esperado, customerService.update("EXEQ", updateCustomerDTO));
    }

    @Test
    public void testUpdateWithNonExistingProductId() {
        // Arrange
        String nonExistingCustomerId = "EXEQU";
        UpdateCustomerDTO updateCustomerDTO = new UpdateCustomerDTO(/* ... */);

        // Simulamos que el producto con el ID proporcionado no existe en el repositorio
        Mockito.when(customersRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        // Act y Assert
        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> customerService.update(nonExistingCustomerId, updateCustomerDTO));

        // Verificamos que se lanza la excepción correcta con el mensaje esperado
        String expectedMessage = String.format("Customer %s inexistente", nonExistingCustomerId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void addProductSucces(){

        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO("EXEQ","Arcor", "Exe", "Hola", "Mendiolaza", "City", "Region", "1234", "Argwntina", "Phone", "233");

        // Configurar el comportamiento del repositorio mock
        Mockito.when(customersRepository.save(Mockito.any(Customer.class))).thenReturn(CUSTOMER);


        // Llamar al método add del servicio
        CustomerResponseDTO customerResponseDTO = customerService.add(createCustomerDTO);

        // Crear un objeto CategoriesResponseDTO manualmente para comparar
        CustomerResponseDTO customerResponseDTOesperado = new CustomerResponseDTO(CUSTOMER);

        // Verificar que el resultado obtenido es el esperado
        assertEquals(customerResponseDTOesperado, customerResponseDTO);
    }


    @Test
    void testDeleteByIdExistingProduct() {
        String existingCustomerId = "EXEQ";

        // Configurar el comportamiento del repositorio para un producto existente
        Mockito.when(customersRepository.existsById(existingCustomerId)).thenReturn(true);

        // Actuar: llamar al método deleteById
        boolean result = customerService.deleteById(existingCustomerId);

        // Verificar que se haya llamado al método deleteById del repositorio con el ID correcto
        Mockito.verify(customersRepository, Mockito.times(1)).deleteById(existingCustomerId);

        // Verificar que el resultado es verdadero (producto existente, eliminado con éxito)
        assertTrue(result);
    }

    @Test
    void testDeleteByIdNonExistingProduct() {
        String existingCustomerId = "EXEQU";

        // Configurar el comportamiento del repositorio para un producto que no existe
        Mockito.when(customersRepository.existsById(existingCustomerId)).thenReturn(false);

        // Actuar: llamar al método deleteById
        boolean result = customerService.deleteById(existingCustomerId);

        // Verificar que no se haya llamado al método deleteById del repositorio
        Mockito.verify(customersRepository, Mockito.never()).deleteById(anyString());

        // Verificar que el resultado es falso (producto no existente, no se eliminó)
        assertFalse(result);
    }

}
