package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.Supplier;

public interface SuppliersRepository extends JpaRepository<Supplier, Long> {
}
