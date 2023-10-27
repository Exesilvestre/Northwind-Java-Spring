package utn.frc.parcial.Api.RequestDto.Updates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEmployeeDTO {


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

    private Long reportsToiD;

    private String photoPath;
}
