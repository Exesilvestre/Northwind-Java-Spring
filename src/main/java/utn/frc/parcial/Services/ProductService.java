package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.*;
import utn.frc.parcial.Repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private CategoriesRepository categoriesRepository;
    private SuppliersRepository suppliersRepository;
    private CustomersRepository customersRepository
    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;

    public ProductService(CategoriesRepository categoriesRepository, SuppliersRepository suppliersRepository, CustomersRepository customersRepository, OrdersRepository ordersRepository, ProductsRepository productsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.suppliersRepository = suppliersRepository;
        this.customersRepository = customersRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
    }

    public List<ProductsResponseDTO> findAll() {
        return productsRepository.findAll().stream()
                .map(product -> new ProductsResponseDTO(product)).toList();
    }


    public Optional<ProductsResponseDTO> findById(Long productId) {
        Optional<Product> product = productsRepository.findById(productId);

        return product.isEmpty()
                ? Optional.empty()
                : Optional.of(new ProductsResponseDTO(product.get()));
    }


    public ProductsResponseDTO add(CreateProductDTO createProductDTO) {
        // Recupera el Supplier de la base de datos utilizando el ID del DTO
        Long supplierId = createProductDTO.getSupplierId();
        Supplier supplier = suppliersRepository.findById(supplierId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Supplier %d inexistente", supplierId))
        );
        // Recupera la Category de la base de datos utilizando el ID del DTO
        Long categoryId = createProductDTO.getCategoryId();
        Category category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Category %d inexistente", categoryId))
        );
        Product product = new Product(createProductDTO, supplier, category);
        return new ProductsResponseDTO(productsRepository.save(product));
    }

    public ProductsResponseDTO update(Long productId, UpdateProductDTO updateProductDTO) {
        Product product = productsRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product %d inexistente", productId))
        );
        // Recupera el Supplier de la base de datos utilizando el ID del DTO
        Long supplierId = updateProductDTO.getSupplierId();
        Supplier supplier = suppliersRepository.findById(supplierId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Supplier %d inexistente", supplierId))
        );
        // Recupera la Category de la base de datos utilizando el ID del DTO
        Long categoryId = updateProductDTO.getCategoryId();
        Category category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Category %d inexistente", categoryId))
        );

        return new ProductsResponseDTO(productsRepository.save(product.update(updateProductDTO, supplier, category)));
    }


    public boolean deleteById(Long productId) {
        if (!productsRepository.existsById(productId)) {
            return false;
        }

        productsRepository.deleteById(productId);
        return true;
    }

}
