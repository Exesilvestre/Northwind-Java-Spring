package utn.frc.parcial.Api.RequestDto.Creates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import utn.frc.parcial.Entities.Category;
import utn.frc.parcial.Entities.Supplier;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProductDTO {

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
