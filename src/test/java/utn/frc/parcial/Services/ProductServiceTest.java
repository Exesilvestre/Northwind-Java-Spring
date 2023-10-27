package utn.frc.parcial.Services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.CategoriesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Product;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.CategoriesRepository;
import utn.frc.parcial.Repositories.ProductsRepository;
import utn.frc.parcial.Repositories.SuppliersRepository;
import utn.frc.parcial.Services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;
    @Mock
    private SuppliersRepository suppliersRepository;
    @Mock
    private ProductsRepository productsRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void getAllSucces(){
        List<Product> products = new ArrayList<>();
        Supplier supplier = new Supplier(3l,"Arcor","Hola","Hola","sdd", "Hola", "Corodba", "2345", "Arg", "23452", "2234", "1233");
        Category category = new Category(1l, "Hola", "Hola");
        Product product = new Product(1l,"Carne",supplier, category, "12", 12.0,2,2,2,"no" );
        products.add(product);
        Mockito.when(productsRepository.findAll()).thenReturn((products));
        List<ProductsResponseDTO> productsResponseDTOS = new ArrayList<>();
        productsResponseDTOS = products.stream().map(p-> new ProductsResponseDTO(p)).toList();

        assertEquals(
                productsResponseDTOS,
                productService.findAll()
        );
    }

    @Test
    public void getAllEmpty(){
        List<ProductsResponseDTO> productsResponseDTOS = new ArrayList<>();
        Mockito.when(productsRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(productsResponseDTOS, productService.findAll());
    }

    @Test
    public void findByIdSucces(){
        Supplier supplier = new Supplier(3l,"Arcor","Hola","Hola","sdd", "Hola", "Corodba", "2345", "Arg", "23452", "2234", "1233");
        Category category = new Category(1l, "Hola", "Hola");
        Product product = new Product(1l,"Carne",supplier, category, "12", 12.0,2,2,2,"no" );

        ProductsResponseDTO productoEsperado = new ProductsResponseDTO(product);

        Mockito.when(productsRepository.findById(1l)).thenReturn(Optional.of(product));

        ProductsResponseDTO result = productService.findById(1l).orElse(null);
        assertEquals(productoEsperado, result);
    }

    @Test public void findbyIDAndnotExiste(){
        Mockito.when(productsRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertEquals(Optional.empty(), productService.findById(anyLong()));
    }

    @Test
    public void updateSucces(){
        // Datos de prueba
        Long productId = 1L;
        Long supplierId = 2L;
        Long categoryId = 3L;

        UpdateProductDTO updateProductDTO = new UpdateProductDTO("Hola", supplierId, categoryId,"12", 12.0,2,2,2,"no");
        Supplier supplier = new Supplier(2l,"Arcor","Hola","Hola","sdd", "Hola", "Corodba", "2345", "Arg", "23452", "2234", "1233");
        Category category = new Category(1l, "Hola", "Hola");
        Product product = new Product(1l,"Carne",supplier, category, "12", 12.0,2,2,2,"no" );
        Product productCambiado = new Product(1l,"Hola",supplier, category, "12", 12.0,2,2,2,"no" );
        Mockito.when(productsRepository.findById(1l)).thenReturn(Optional.of(product));

        Mockito.when(suppliersRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        Mockito.when(categoriesRepository.findById(anyLong())).thenReturn(Optional.of(category));
        Mockito.when(productsRepository.save(Mockito.any(Product.class))).thenReturn(productCambiado);


        ProductsResponseDTO esperado = new ProductsResponseDTO(1l,"Hola",supplier.getId(), category.getId(), "12", 12.0,2,2,2,"no" );

        assertEquals(esperado, productService.update(1l, updateProductDTO));
    }


    @Test
    public void testUpdateWithNonExistingProductId() {
        // Arrange
        Long nonExistingProductId = 1L;
        UpdateProductDTO updateProductDTO = new UpdateProductDTO(/* ... */);

        // Simulamos que el producto con el ID proporcionado no existe en el repositorio
        Mockito.when(productsRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());


        // Act y Assert
        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.update(nonExistingProductId, updateProductDTO));

        // Verificamos que se lanza la excepción correcta con el mensaje esperado
        String expectedMessage = String.format("Product %d inexistente", nonExistingProductId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void addProductSucces(){
        Long supplierId = 2L;
        Long categoryId = 1L;
        CreateProductDTO createProductDTO = new CreateProductDTO("Carne", supplierId, categoryId,"12", 12.0,2,2,2,"no");
        Supplier supplier = new Supplier(2l,"Arcor","Hola","Hola","sdd", "Hola", "Corodba", "2345", "Arg", "23452", "2234", "1233");
        Category category = new Category(1l, "Hola", "Hola");
        Product product = new Product(1l,"Carne",supplier, category, "12", 12.0,2,2,2,"no" );


        // Configurar el comportamiento del repositorio mock
        Mockito.when(productsRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(suppliersRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        Mockito.when(categoriesRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Llamar al método add del servicio
        ProductsResponseDTO productsResponseDTO = productService.add(createProductDTO);

        // Crear un objeto CategoriesResponseDTO manualmente para comparar
        ProductsResponseDTO productsResponseesperado = new ProductsResponseDTO(product);

        // Verificar que el resultado obtenido es el esperado
        assertEquals(productsResponseesperado, productsResponseDTO);
    }


    @Test
    void testDeleteByIdExistingProduct() {
        Long existingProductId = 1L;

        // Configurar el comportamiento del repositorio para un producto existente
        Mockito.when(productsRepository.existsById(existingProductId)).thenReturn(true);

        // Actuar: llamar al método deleteById
        boolean result = productService.deleteById(existingProductId);

        // Verificar que se haya llamado al método deleteById del repositorio con el ID correcto
        Mockito.verify(productsRepository, Mockito.times(1)).deleteById(existingProductId);

        // Verificar que el resultado es verdadero (producto existente, eliminado con éxito)
        assertTrue(result);
    }

    @Test
    void testDeleteByIdNonExistingProduct() {
        Long nonExistingProductId = 2L;

        // Configurar el comportamiento del repositorio para un producto que no existe
        Mockito.when(productsRepository.existsById(nonExistingProductId)).thenReturn(false);

        // Actuar: llamar al método deleteById
        boolean result = productService.deleteById(nonExistingProductId);

        // Verificar que no se haya llamado al método deleteById del repositorio
        Mockito.verify(productsRepository, Mockito.never()).deleteById(anyLong());

        // Verificar que el resultado es falso (producto no existente, no se eliminó)
        assertFalse(result);
    }

}




