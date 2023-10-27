package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.parcial.Entities.OrderDetail;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, OrderDetail.OrderDetailId> {

}
