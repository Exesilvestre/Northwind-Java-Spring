package utn.frc.parcial.Api.RequestDto.Updates;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOrderDetailDTO {



        @NotNull
        private double unitPrice;
        @NotNull
        private int quantity;
        @NotNull
        private double discount;
    }


