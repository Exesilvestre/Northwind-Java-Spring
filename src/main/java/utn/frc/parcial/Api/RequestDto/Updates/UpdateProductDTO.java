package utn.frc.parcial.Api.RequestDto.Updates;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateProductDTO {

    private String productName;
    private Long supplierId;
    private Long categoryId;
    private String quantityPerUnit;
    @Builder.Default()
    private Double unitPrice = 0.0;
    @Builder.Default()
    private Integer unitsInStock = 0;
    @Builder.Default()
    private Integer unitOnOrder = 0;
    @Builder.Default()
    private Integer reorderLevel = 0;
    @Builder.Default()
    @NotBlank(message = "Se necesita un discontinued")
    private String discontinued = "0";
}
