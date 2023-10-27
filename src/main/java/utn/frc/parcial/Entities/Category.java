package utn.frc.parcial.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCategoryDTO;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "Categories")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Column(name = "CategoryID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CategoryName")
    private String category;


    @Column(name = "Description")
    private String description;


/*
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

 */

    public Category(CreateCategoryDTO createCategoryDTO) {
        category = createCategoryDTO.getCategoryName();
        description = createCategoryDTO.getDescription();
    }


    public Category update(UpdateCategoryDTO updateCategoryDTO){
        category = updateCategoryDTO.getCategory();
        description = updateCategoryDTO.getDescription();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(category, category.category) &&
                Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, description);
    }
}
