package utn.frc.parcial.Api.Controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDetailDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDetailDTO;
import utn.frc.parcial.Api.ResponseDto.OrderDetailResponseDTO;
import utn.frc.parcial.Api.ResponseDto.OrderRepsonseDTO;
import utn.frc.parcial.Services.OrderDetailService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailsController {

    private OrderDetailService orderDetailService;

    public OrderDetailsController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping()
    public ResponseEntity<List<OrderDetailResponseDTO>> findOrdersDetails(){
        List<OrderDetailResponseDTO> orders =orderDetailService.findAll();
        return orders.isEmpty()
                ?ResponseEntity.noContent().build()
                :ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailResponseDTO> findOrderDetail(@PathVariable Long orderId, @PathVariable Long productId) {
        Optional<OrderDetailResponseDTO> orderDetail = orderDetailService.findById(orderId, productId);
        return orderDetail.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(orderDetail.get());
    }

    @PostMapping
    public ResponseEntity<OrderDetailResponseDTO> addOrderDetail(@RequestBody @Valid CreateOrderDetailDTO orderDetailDTO) {
        return new ResponseEntity<>(orderDetailService.add(orderDetailDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderDetail(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateOrderDetailDTO orderDetailDTO
    ) {
        return new ResponseEntity<>(orderDetailService.update(orderId,productId, orderDetailDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity deleteOrder(@PathVariable Long orderId,@PathVariable Long productId ) {
        return orderDetailService.deleteById(orderId, productId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
