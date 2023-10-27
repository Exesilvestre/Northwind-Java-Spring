package utn.frc.parcial.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frc.parcial.Api.RequestDto.Creates.CreateEmployeeDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateEmployeeDTO;
import utn.frc.parcial.Entities.Utils.ConverterFechas;
import utn.frc.parcial.Services.EmployeeService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Employees")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private Long employeeID;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "Title")
    private String title;

    @Column(name = "TitleOfCourtesy")
    private String titleOfCourtesy;



    @Column(name = "BirthDate")
    private LocalDate birthDate;


    @Column(name = "HireDate")
    private LocalDate hireDate;

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

    @Column(name = "HomePhone")
    private String homePhone;

    @Column(name = "Extension")
    private String extension;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "Notes")
    private String notes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID")
    private Employee reportsTo;

    @Column(name = "PhotoPath")
    private String photoPath;

    public Employee(Employee employee, CreateEmployeeDTO createEmployeeDTO) {
        lastName = createEmployeeDTO.getLastName();
        firstName = createEmployeeDTO.getFirstName();
        title = createEmployeeDTO.getTitle();
        titleOfCourtesy = createEmployeeDTO.getTitleOfCourtesy();
        birthDate = createEmployeeDTO.getBirthDate();
        hireDate = createEmployeeDTO.getHireDate();
        address = createEmployeeDTO.getAddress();
        city = createEmployeeDTO.getCity();
        region = createEmployeeDTO.getRegion();
        postalCode = createEmployeeDTO.getPostalCode();
        country = createEmployeeDTO.getCountry();
        homePhone = createEmployeeDTO.getHomePhone();
        extension = createEmployeeDTO.getExtension();
        photo = createEmployeeDTO.getPhoto();
        notes = createEmployeeDTO.getNotes();
        reportsTo = employee;
        photoPath = createEmployeeDTO.getPhotoPath();
    }

    public Employee update(UpdateEmployeeDTO updateEmployeeDTO, Employee employee){
        lastName = updateEmployeeDTO.getLastName();
        firstName = updateEmployeeDTO.getFirstName();
        title = updateEmployeeDTO.getTitle();
        titleOfCourtesy = updateEmployeeDTO.getTitleOfCourtesy();
        birthDate = updateEmployeeDTO.getBirthDate();
        hireDate = updateEmployeeDTO.getHireDate();
        address = updateEmployeeDTO.getAddress();
        city = updateEmployeeDTO.getCity();
        region = updateEmployeeDTO.getRegion();
        postalCode = updateEmployeeDTO.getPostalCode();
        country = updateEmployeeDTO.getCountry();
        homePhone = updateEmployeeDTO.getHomePhone();
        extension = updateEmployeeDTO.getExtension();
        photo = updateEmployeeDTO.getPhoto();
        notes = updateEmployeeDTO.getNotes();
        reportsTo = employee;
        photoPath = updateEmployeeDTO.getPhotoPath();

        return  this;
    }
}
