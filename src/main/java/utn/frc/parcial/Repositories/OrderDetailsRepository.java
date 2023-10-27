package utn.frc.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.frc.parcial.Api.ResponseDto.OrderDetailResponseDTO;
import utn.frc.parcial.Entities.Order;
import utn.frc.parcial.Entities.OrderDetail;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, OrderDetail.OrderDetailId> {
    @Query("SELECT od FROM OrderDetail od WHERE od.id.order = ?1")
    List<OrderDetail> findByOrderId(Order order);
}
