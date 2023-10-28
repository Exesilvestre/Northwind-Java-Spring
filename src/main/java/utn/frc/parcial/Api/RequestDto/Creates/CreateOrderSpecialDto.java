package utn.frc.parcial.Api.RequestDto.Creates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderSpecialDto {
    private Long supplierId;
    private Long categoryId;

    private Integer stockRequerido;

    private String customerId;
    private Long employeeId;
    private Long shipperId;

}
