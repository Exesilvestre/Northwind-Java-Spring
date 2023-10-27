package utn.frc.parcial.Api.RequestDto.Creates;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCustomerDTO {
    @NotBlank(message = "id es necesario")
    private String id;
    @NotBlank(message = "Company name es necesario")
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

}
