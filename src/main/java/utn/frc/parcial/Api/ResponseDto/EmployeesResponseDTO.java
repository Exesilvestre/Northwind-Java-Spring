package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Employee;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesResponseDTO {
    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String extension;
    private String photo;
    private String notes;
    private long reportsTo;
    private String photoPath;

    public EmployeesResponseDTO(Employee employee) {
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.title = employee.getTitle();
        this.titleOfCourtesy = employee.getTitleOfCourtesy();
        this.birthDate = employee.getBirthDate();
        this.hireDate = employee.getHireDate();
        this.address = employee.getAddress();
        this.city = employee.getCity();
        this.region = employee.getRegion();
        this.postalCode = employee.getPostalCode();
        this.country = employee.getCountry();
        this.homePhone = employee.getHomePhone();
        this.extension = employee.getExtension();
        this.photo = employee.getPhoto();
        this.notes = employee.getNotes();
        this.reportsTo = employee.getReportsTo().getEmployeeID();
        this.photoPath = employee.getPhotoPath();
    }
}
