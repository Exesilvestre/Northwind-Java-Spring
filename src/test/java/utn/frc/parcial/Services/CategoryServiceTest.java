package utn.frc.parcial.Services;

import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.CategoriesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Product;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.CategoriesRepository;
import utn.frc.parcial.Services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;
    @InjectMocks
    private CategoryService categoryService;

    private final Category CATEGORY = new Category(1l, "Carne", "Todo carne");


    @Test
    void testFindAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(CATEGORY);
        Mockito.when(categoriesRepository.findAll()).thenReturn(categories);

        assertEquals(
                categories,
                categoriesRepository.findAll()
        );
    }

    @Test
    void testFindAllEmpty() {
        Mockito.when(categoriesRepository.findAll()).thenReturn(new ArrayList<>());

        List<Category> expectedCategories = new ArrayList<>();
        List<Category> actualCategories = categoriesRepository.findAll();

        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void testFindById() {
        Mockito.when(categoriesRepository.findById(anyLong())).thenReturn(Optional.of(CATEGORY));

        Optional<Category> result = categoriesRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(CATEGORY, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        Mockito.when(categoriesRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Category> category = categoriesRepository.findById(1l);

        assertEquals(
                Optional.empty(),
                categoriesRepository.findById(999l)
        );
    }
    @Test
    void testAddSuccess() {
        // Crear una nueva categoría y su correspondiente DTO
        Category newCategory = new Category(2l, "Pollo", "Todo pollo");
        CreateCategoryDTO categoryDTO = new CreateCategoryDTO("Pollo", "Todo Pollo", null);

        // Configurar el comportamiento del repositorio mock
        Mockito.when(categoriesRepository.save(Mockito.any(Category.class))).thenReturn(newCategory);

        // Llamar al método add del servicio
        CategoriesResponseDTO addedCategoryDTO = categoryService.add(categoryDTO);

        // Crear un objeto CategoriesResponseDTO manualmente para comparar
        CategoriesResponseDTO expectedResponseDTO = new CategoriesResponseDTO(newCategory.getId(), newCategory.getCategory(), newCategory.getDescription());

        // Verificar que el resultado obtenido es el esperado
        assertEquals(expectedResponseDTO, addedCategoryDTO);

    }





    @Test
    public void actualizarCategoriaNotFound() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();
        updateCategoryDTO.setCategory("Name");
        updateCategoryDTO.setDescription("Description");

        // Mock the findById method to return an empty Optional (category not found)
        when(categoriesRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Verify that the service method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.update(1L, updateCategoryDTO);
        });
    }






}
