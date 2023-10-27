package utn.frc.parcial.Api.RequestDto.Creates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCategoryDTO {
    private String categoryName;
    private String description;
    private byte[] picture;

}
