package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.OrderDetail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {

    private long orderId;
    private long productId;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;

    public OrderDetailResponseDTO(OrderDetail orderDetail) {
        this.orderId = orderDetail.getId().getOrder().getId();
        this.productId = orderDetail.getId().getProduct().getId();
        this.unitPrice = orderDetail.getUnitPrice();
        this.quantity = orderDetail.getQuantity();
        this.discount = orderDetail.getDiscount();
    }
}
