package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsByCategoryAndEmplResponseDTO {
    private Long productId;
    private String productName;
    private Integer stockFuturo;
    private Double unitPrice;
}
