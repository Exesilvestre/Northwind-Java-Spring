package utn.frc.parcial.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "Products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Column(name = "ProductID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProductName")
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryID")
    private Category category;


    @Column(name = "QuantityPerUnit")
    private String quantityPerUnit;


    @Column(name = "UnitPrice")
    private Double unitPrice;

    @Column(name = "UnitsInStock")
    private Integer unitsInStock;

    @Column(name = "UnitsOnOrder")
    private Integer unitOnOrder;

    @Column(name = "ReorderLevel")
    private Integer reorderLevel;

    @Column(name = "Discontinued")
    private String discontinued;

    @JsonIgnore
    @OneToMany(mappedBy = "id.product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;


    public Product(CreateProductDTO createProductDTO, Supplier asupplier, Category acategory){

        productName = createProductDTO.getProductName();
        supplier = asupplier;
        category = acategory;
        quantityPerUnit = createProductDTO.getQuantityPerUnit();
        unitPrice = createProductDTO.getUnitPrice();
        unitOnOrder = createProductDTO.getUnitOnOrder();
        unitsInStock = createProductDTO.getUnitsInStock();
        reorderLevel = createProductDTO.getReorderLevel();
        discontinued = createProductDTO.getDiscontinued();

    }

    public Product(Long id, String productName, Supplier supplier, Category category, String quantityPerUnit, Double unitPrice, Integer unitsInStock, Integer unitOnOrder, Integer reorderLevel, String discontinued) {
        this.id = id;
        this.productName = productName;
        this.supplier = supplier;
        this.category = category;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitOnOrder = unitOnOrder;
        this.reorderLevel = reorderLevel;
        this.discontinued = discontinued;
    }

    public Product update(UpdateProductDTO updateProductDTO, Supplier asupplier, Category acategory){
        productName = updateProductDTO.getProductName();
        supplier = asupplier;
        category = acategory;
        quantityPerUnit = updateProductDTO.getQuantityPerUnit();
        unitPrice = updateProductDTO.getUnitPrice();
        unitOnOrder = updateProductDTO.getUnitOnOrder();
        unitsInStock = updateProductDTO.getUnitsInStock();
        reorderLevel = updateProductDTO.getReorderLevel();
        discontinued = updateProductDTO.getDiscontinued();
        return this;
    }
}
