package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {

}
