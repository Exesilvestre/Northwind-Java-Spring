package utn.frc.parcial.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDTO;
import utn.frc.parcial.Entities.Utils.ConverterFechas;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "Orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Column(name = "OrderID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerID")
    private Customer customer;


    @OneToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @Column(name = "OrderDate")
    private LocalDate orderDate;

    @Column(name = "RequiredDate")
    private LocalDate requiredDate;

    @Column(name = "ShippedDate")
    private LocalDate shippedDate;

/*
    @Convert(converter = ConverterFechas.class)
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Convert(converter = ConverterFechas.class)
    @Column(name = "RequiredDate")
    private LocalDateTime requiredDate;

    @Convert(converter = ConverterFechas.class)
    @Column(name = "ShippedDate")
    private LocalDateTime shippedDate;

 */

    @Column(name = "ShipVia")
    private Integer shipVia;

    @Column(name = "Freight")
    private Double freight;

    @Column(name = "ShipName")
    private String shipName;

    @Column(name = "ShipAddress")
    private String shipAddress;

    @Column(name = "ShipCity")
    private String shipCity;

    @Column(name = "ShipRegion")
    private String shipRegion;

    @Column(name = "ShipPostalCode")
    private String shipPostalCode;

    @Column(name = "ShipCountry")
    private String shipCountry;

    @OneToMany(mappedBy = "id.order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;


    public Order(CreateOrderDTO createOrderDTO, Customer acustomer, Employee aemployee){
        employee = aemployee;
        customer = acustomer;
        orderDate = createOrderDTO.getOrderDate();
        requiredDate = createOrderDTO.getRequiredDate();
        shippedDate = createOrderDTO.getShippedDate();
        shipVia = createOrderDTO.getShipVia();
        freight = createOrderDTO.getFreight();
        shipName = createOrderDTO.getShipName();
        shipAddress = createOrderDTO.getShipAddress();
        shipCity = createOrderDTO.getShipCity();
        shipCountry = createOrderDTO.getShipCountry();
        shipPostalCode = createOrderDTO.getShipPostalCode();
        shipRegion = createOrderDTO.getShipRegion();
    }

    public Order update(UpdateOrderDTO updateOrderDTO, Customer acustomer, Employee aemployee){
        employee = aemployee;
        customer = acustomer;
        orderDate = updateOrderDTO.getOrderDate();
        requiredDate = updateOrderDTO.getRequiredDate();
        shippedDate = updateOrderDTO.getShippedDate();
        shipVia = updateOrderDTO.getShipVia();
        freight = updateOrderDTO.getFreight();
        shipName = updateOrderDTO.getShipName();
        shipAddress = updateOrderDTO.getShipAddress();
        shipCity = updateOrderDTO.getShipCity();
        shipCountry = updateOrderDTO.getShipCountry();
        shipPostalCode = updateOrderDTO.getShipPostalCode();
        shipRegion = updateOrderDTO.getShipRegion();
        return this;
    }




}
