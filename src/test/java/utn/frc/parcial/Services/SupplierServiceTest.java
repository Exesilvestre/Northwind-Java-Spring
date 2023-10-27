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
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Api.ResponseDto.SuppliersResponseDTO;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.SuppliersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {
    @Mock
    private SuppliersRepository suppliersRepository;
    @InjectMocks
    private SupplierService supplierService;

    private final Supplier SUPPLIER = new Supplier(1l, "Arcor", "Exe", "Exeq", "san jose", "Mend", "Cord", "224", "Arg", "123453", "235", "ekkf.e");

    @Test
    public void getAllSucces(){
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(SUPPLIER);
        Mockito.when(suppliersRepository.findAll()).thenReturn((suppliers));
        List<SuppliersResponseDTO> suppliersResponseDTOS = new ArrayList<>();
        suppliersResponseDTOS = suppliers.stream().map(p-> new SuppliersResponseDTO(p)).toList();

        assertEquals(
                suppliersResponseDTOS,
                supplierService.findAll()
        );
    }

    @Test
    public void getAllEmpty(){
        List<SuppliersResponseDTO> suppliersResponseDTOS = new ArrayList<>();
        Mockito.when(suppliersRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(suppliersResponseDTOS, supplierService.findAll());
    }

    @Test
    public void findByIdSucces(){


        SuppliersResponseDTO suppliersEsperado = new SuppliersResponseDTO(SUPPLIER);

        Mockito.when(suppliersRepository.findById(anyLong())).thenReturn(Optional.of(SUPPLIER));

        SuppliersResponseDTO result = supplierService.findById(1L).orElse(null);
        assertEquals(suppliersEsperado, result);
    }

    @Test
    public void findbyIDAndnotExiste(){
        Mockito.when(suppliersRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertEquals(Optional.empty(), supplierService.findById(anyLong()));
    }

    @Test
    public void updateSucces(){

        UpdateSupplierDTO updateSupplierDTO = new UpdateSupplierDTO( "Arcor", "Exe", "Exeq", "san jose", "Mend", "Cord", "224", "Arg", "123453", "235", "ekkf.e");
        Supplier supplierCambiado = new Supplier(1l,"Bagley", "Exe", "Exeq", "san jose", "Mend", "Cord", "224", "Arg", "123453", "235", "ekkf.e");
        Mockito.when(suppliersRepository.findById(anyLong())).thenReturn(Optional.of(SUPPLIER));

        Mockito.when(suppliersRepository.save(Mockito.any(Supplier.class))).thenReturn(supplierCambiado);


        SuppliersResponseDTO esperado = new SuppliersResponseDTO(1l,"Bagley", "Exe", "Exeq", "san jose", "Mend", "Cord", "224", "Arg", "123453", "235", "ekkf.e" );

        assertEquals(esperado, supplierService.update(1l, updateSupplierDTO));
    }

    @Test
    public void testUpdateWithNonExistingProductId() {
        // Arrange
        Long nonExistingSupplierId = 2l;
        UpdateSupplierDTO updateSupplierDTO = new UpdateSupplierDTO(/* ... */);

        // Simulamos que el supplier con el ID proporcionado no existe en el repositorio
        Mockito.when(suppliersRepository.findById(nonExistingSupplierId)).thenReturn(Optional.empty());

        // Act y Assert
        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> supplierService.update(nonExistingSupplierId, updateSupplierDTO));

        // Verificamos que se lanza la excepción correcta con el mensaje esperado
        String expectedMessage = String.format("Supplier %d inexistente", nonExistingSupplierId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void addProductSucces(){

        CreateSupplierDTO createSupplierDTO = new CreateSupplierDTO( "Arcor", "Exe", "Exeq", "san jose", "Mend", "Cord", "224", "Arg", "123453", "235", "ekkf.e");

        // Configurar el comportamiento del repositorio mock
        Mockito.when(suppliersRepository.save(Mockito.any(Supplier.class))).thenReturn(SUPPLIER);


        // Llamar al método add del servicio
        SuppliersResponseDTO suppliersResponseDTO = supplierService.add(createSupplierDTO);

        // Crear un objeto SupplierResponseDTO manualmente para comparar
        SuppliersResponseDTO suppliersResponseDTOesperado = new SuppliersResponseDTO(SUPPLIER);

        // Verificar que el resultado obtenido es el esperado
        assertEquals(suppliersResponseDTOesperado, suppliersResponseDTO);
    }


    @Test
    void testDeleteByIdExisting() {
        Long existingSupplierId = 1l;

        // Configurar el comportamiento del repositorio para un producto existente
        Mockito.when(suppliersRepository.existsById(existingSupplierId)).thenReturn(true);

        // Actuar: llamar al método deleteById
        boolean result = supplierService.deleteById(existingSupplierId);

        // Verificar que se haya llamado al método deleteById del repositorio con el ID correcto
        Mockito.verify(suppliersRepository, Mockito.times(1)).deleteById(existingSupplierId);

        // Verificar que el resultado es verdadero (producto existente, eliminado con éxito)
        assertTrue(result);
    }

    @Test
    void testDeleteByIdNonExisting() {
        Long NonExistingCustomerId = 2l;

        // Configurar el comportamiento del repositorio para un producto que no existe
        Mockito.when(suppliersRepository.existsById(NonExistingCustomerId)).thenReturn(false);

        // Actuar: llamar al método deleteById
        boolean result = supplierService.deleteById(NonExistingCustomerId);

        // Verificar que no se haya llamado al método deleteById del repositorio
        Mockito.verify(suppliersRepository, Mockito.never()).deleteById(anyLong());

        // Verificar que el resultado es falso (producto no existente, no se eliminó)
        assertFalse(result);
    }

}
