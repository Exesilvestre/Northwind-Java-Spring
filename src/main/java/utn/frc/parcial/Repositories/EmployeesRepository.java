package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.Employee;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {
}
