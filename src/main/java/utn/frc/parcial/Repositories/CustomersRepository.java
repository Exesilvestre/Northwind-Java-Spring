package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.Customer;

public interface CustomersRepository extends JpaRepository<Customer, String> {
}
