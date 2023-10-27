package utn.frc.parcial.Entities;

import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDetailDTO;

import java.io.Serializable;


@Table(name = "Order Details")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {


    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Discount")
    private double discount;

    public OrderDetail(CreateOrderDetailDTO createOrderDetailDTO, OrderDetailId aorderDetailId) {
        id = aorderDetailId;
        unitPrice = createOrderDetailDTO.getUnitPrice();
        quantity = createOrderDetailDTO.getQuantity();
        discount = createOrderDetailDTO.getDiscount();
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Data
    @Embeddable
    public static class OrderDetailId implements Serializable {

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
        private Order order;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
        private Product product;

        public OrderDetailId(Long aorderId, Long aproductId) {
        }

        // Getters y setters
    }

    // Resto de las propiedades, getters y setters
}
