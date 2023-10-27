package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Category;

import java.sql.Blob;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesResponseDTO {

    private Long id;
    private String categoryName;
    private String description;
    //private byte[] picture;

    public CategoriesResponseDTO(Category category){
        id = category.getId();
        categoryName = category.getCategory();
        description = category.getDescription();
        //picture = category.getPicture();
    }
}
