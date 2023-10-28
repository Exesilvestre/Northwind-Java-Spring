package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Product;
import utn.frc.parcial.Entities.Supplier;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySupplierAndCategory(Supplier supplier, Category category);
}
