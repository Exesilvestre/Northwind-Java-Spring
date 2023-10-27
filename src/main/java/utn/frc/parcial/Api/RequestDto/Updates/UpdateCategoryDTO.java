package utn.frc.parcial.Api.RequestDto.Updates;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCategoryDTO {

    private String category;

    private String description;



}

