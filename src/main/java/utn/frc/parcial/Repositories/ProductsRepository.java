package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Entities.Product;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {

}
