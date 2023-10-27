package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Entities.Order;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long>  {

}
