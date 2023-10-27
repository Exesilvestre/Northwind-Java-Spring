package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRepsonseDTO {
    long orderID;
    String customerID;
    Long employeeID;
    LocalDate orderDate;
    LocalDate requiredDate;
    LocalDate shippedDate;
    int shipVia;
    Double freight;
    String shipName;
    String shipAddress;
    String shipCity;
    String shipRegion;
    String shipPostalCode;
    String shipCountry;

    public OrderRepsonseDTO(Order order) {
        orderID = order.getId();
        customerID = order.getCustomer().getId();
        employeeID =  order.getEmployee().getEmployeeID();
        orderDate = order.getOrderDate();
        requiredDate = order.getRequiredDate();
        shippedDate = order.getShippedDate();
        shipVia = order.getShipVia();
        freight = order.getFreight();
        shipName = order.getShipName();
        shipAddress = order.getShipAddress();
        shipCity = order.getShipCity();
        shipRegion = order.getShipRegion();
        shipPostalCode = order.getShipPostalCode();
        shipCountry = order.getShipCountry();
    }
}
