package utn.frc.parcial.Api.RequestDto.Creates;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSupplierDTO {
    @NotBlank(message = "Company name required")
    private String companyName;
    private String contactName;
    private String titleName;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;
    private String homePage;
}
