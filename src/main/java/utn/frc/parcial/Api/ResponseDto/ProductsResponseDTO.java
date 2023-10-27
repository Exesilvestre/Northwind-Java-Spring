package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponseDTO {
    private Long id;
    private String productName;
    private Long supplierId;
    private Long categoryId;
    private String quantityPerUnit;

    private Double unitPrice;

    private Integer unitsInStock;

    private Integer unitOnOrder;

    private Integer reorderLevel;

    private String discontinued;

    public ProductsResponseDTO(Product product){
        id = product.getId();
        productName = product.getProductName();
        supplierId = product.getSupplier().getId();
        categoryId = product.getCategory().getId();
        quantityPerUnit = product.getQuantityPerUnit();
        unitPrice = product.getUnitPrice();
        unitsInStock = product.getUnitsInStock();
        unitOnOrder = product.getUnitOnOrder();
        reorderLevel = product.getReorderLevel();
        discontinued = product.getDiscontinued();
    }
}

