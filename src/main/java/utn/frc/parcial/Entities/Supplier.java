package utn.frc.parcial.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;

import java.util.HashSet;
import java.util.Set;

@Table(name = "Suppliers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Supplier {

    @Column(name = "SupplierID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "HomePage")
    private String homePage;



    public Supplier(CreateSupplierDTO createSupplierDTO) {
        companyName = createSupplierDTO.getCompanyName();
        contactName = createSupplierDTO.getContactName();;
        contactTitle = createSupplierDTO.getTitleName();
        address = createSupplierDTO.getAddress();;
        city = createSupplierDTO.getCity();
        region = createSupplierDTO.getRegion();
        country = createSupplierDTO.getCountry();
        phone = createSupplierDTO.getPhone();
        fax = createSupplierDTO.getFax();
    }

    public Supplier update(UpdateSupplierDTO updateSupplierDTO){
        companyName = updateSupplierDTO.getCompanyName();
        contactName = updateSupplierDTO.getContactName();;
        contactTitle = updateSupplierDTO.getTitleName();
        address = updateSupplierDTO.getAddress();;
        city = updateSupplierDTO.getCity();
        region = updateSupplierDTO.getRegion();
        country = updateSupplierDTO.getCountry();
        phone = updateSupplierDTO.getPhone();
        fax = updateSupplierDTO.getFax();
        return this;
    }


}
