package utn.frc.parcial.Api.Controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.OrderRepsonseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<List<OrderRepsonseDTO>> findOrders(){
        List<OrderRepsonseDTO> orders =orderService.findAll();
        return orders.isEmpty()
                ?ResponseEntity.noContent().build()
                :ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRepsonseDTO> findOrder(@PathVariable Long id) {
        Optional<OrderRepsonseDTO> order = orderService.findById(id);

        return order.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(order.get());
    }

    @PostMapping
    public ResponseEntity<OrderRepsonseDTO> addOrder(@RequestBody @Valid CreateOrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.add(orderDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderRepsonseDTO> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderDTO orderDTO
    ) {
        return new ResponseEntity<>(orderService.update(id, orderDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        return orderService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
