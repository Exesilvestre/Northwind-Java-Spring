package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String id;
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

    public CustomerResponseDTO(Customer customer){
        id = customer.getId();
        companyName = customer.getCompanyName();
        contactName = customer.getContactName();
        contactTitle = customer.getContactTitle();
        address = customer.getAddress();
        city = customer.getCity();
        region = customer.getRegion();
        postalCode = customer.getPostalCode();
        country = customer.getCountry();
        phone = customer.getPhone();
        fax = customer.getFax();
    }
}
