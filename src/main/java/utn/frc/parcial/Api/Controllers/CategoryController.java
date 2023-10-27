package utn.frc.parcial.Api.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.ResponseDto.CategoriesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Services.CategoryService;
import utn.frc.parcial.Services.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<CategoriesResponseDTO>> findCategories() {

        List<CategoriesResponseDTO> categories = categoryService.findAll();

        return categories.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesResponseDTO> findCategory(@PathVariable Long id) {
        Optional<CategoriesResponseDTO> category = categoryService.findById(id);

        return category.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(category.get());
    }


    @PostMapping
    public ResponseEntity<CategoriesResponseDTO> addCategory(@RequestBody @Valid CreateCategoryDTO category) {
        try {
            return new ResponseEntity<>(categoryService.add(category), HttpStatus.CREATED);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoriesResponseDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryDTO categoryDTO
    ) {
        return new ResponseEntity<>(categoryService.update(id, categoryDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        return categoryService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}
