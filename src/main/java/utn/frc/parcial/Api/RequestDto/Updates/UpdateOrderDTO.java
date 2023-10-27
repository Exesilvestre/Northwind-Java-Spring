package utn.frc.parcial.Api.RequestDto.Updates;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderDTO {
    long orderID;
    String customerID;
    Long employeeID;
    LocalDate orderDate;
    LocalDate requiredDate;
    LocalDate shippedDate;
    int shipVia;
    @Builder.Default
    Double freight = 0.0;
    String shipName;
    String shipAddress;
    String shipCity;
    String shipRegion;
    String shipPostalCode;
    String shipCountry;
}
