package utn.frc.parcial.Api.RequestDto.Creates;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import utn.frc.parcial.Entities.Employee;
import utn.frc.parcial.Entities.OrderDetail;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateOrderDetailDTO {

    @NotNull
    private Long orderId;
    @NotNull
    private Long productId;
    @NotNull
    private double unitPrice;
    @NotNull
    private int quantity;
    @NotNull
    private double discount;
}
