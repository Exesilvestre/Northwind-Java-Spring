package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Supplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuppliersResponseDTO {
    private Long id;
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

    public SuppliersResponseDTO(Supplier supplier){
        id = supplier.getId();
        companyName = supplier.getCompanyName();
        contactName = supplier.getContactName();
        titleName = supplier.getContactTitle();
        address = supplier.getAddress();
        city = supplier.getCity();
        region = supplier.getRegion();
        postalCode = supplier.getPostalCode();
        country = supplier.getCountry();
        phone = supplier.getPhone();
        fax = supplier.getFax();
        homePage = supplier.getHomePage();
    }
}
