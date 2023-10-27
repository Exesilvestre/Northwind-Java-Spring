package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.ResponseDto.CategoriesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Repositories.CategoriesRepository;
import utn.frc.parcial.Repositories.CustomersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoriesRepository categoriesRepository;

    public CategoryService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<CategoriesResponseDTO> findAll() {
        return categoriesRepository.findAll().stream().map(category -> new CategoriesResponseDTO(category)).toList();
    }

    public Optional<CategoriesResponseDTO> findById(Long categoryId) {
        Optional<Category> category = categoriesRepository.findById(categoryId);

        return category.isEmpty()
                ? Optional.empty()
                : Optional.of(new CategoriesResponseDTO(category.get()));
    }


    public CategoriesResponseDTO add(CreateCategoryDTO createCategoryDTO) {
        Category category = new Category(createCategoryDTO);
        return new CategoriesResponseDTO(categoriesRepository.save(category));
    }

    public CategoriesResponseDTO update(Long categoryId, UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Category %d inexistente", categoryId))
        );


        return new CategoriesResponseDTO(categoriesRepository.save(category.update(updateCategoryDTO)));
    }


    public boolean deleteById(Long categoryId) {
        if (!categoriesRepository.existsById(categoryId)) {
            return false;
        }

        categoriesRepository.deleteById(categoryId);
        return true;
    }
}
