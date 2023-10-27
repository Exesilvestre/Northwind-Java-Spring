package utn.frc.parcial.Entities;


import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;

@Table(name = "Customers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Column(name = "CustomerID")
    @Id
    private String id;

    @Column(name = "CompanyName")
    private String companyName;

    @Column(name = "ContactName")
    private String contactName;

    @Column(name = "ContactTitle")
    private String contactTitle;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "Region")
    private String region;

    @Column(name = "PostalCode")
    private String postalCode;

    @Column(name = "Country")
    private String country;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Fax")
    private String fax;

    public Customer(CreateCustomerDTO createCustomerDTO) {
        id = createCustomerDTO.getId();
        companyName = createCustomerDTO.getCompanyName();
        contactName = createCustomerDTO.getContactName();;
        contactTitle = createCustomerDTO.getContactTitle();
        address = createCustomerDTO.getAddress();;
        city = createCustomerDTO.getCity();
        region = createCustomerDTO.getRegion();
        postalCode = createCustomerDTO.getPostalCode();
        country = createCustomerDTO.getCountry();
        phone = createCustomerDTO.getPhone();
        fax = createCustomerDTO.getFax();
    }

    public Customer update(UpdateCustomerDTO updateCustomerDTO){
        companyName = updateCustomerDTO.getCompanyName();
        contactName = updateCustomerDTO.getContactName();;
        contactTitle = updateCustomerDTO.getContactTitle();
        address = updateCustomerDTO.getAddress();;
        city = updateCustomerDTO.getCity();
        region = updateCustomerDTO.getRegion();
        country = updateCustomerDTO.getCountry();
        phone = updateCustomerDTO.getPhone();
        fax = updateCustomerDTO.getFax();
        postalCode = updateCustomerDTO.getPostalCode();

        return this;
    }
}
