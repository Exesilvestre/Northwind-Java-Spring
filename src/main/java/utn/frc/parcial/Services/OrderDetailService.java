package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDetailDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDetailDTO;
import utn.frc.parcial.Api.ResponseDto.OrderDetailResponseDTO;
import utn.frc.parcial.Entities.*;
import utn.frc.parcial.Repositories.OrderDetailsRepository;
import utn.frc.parcial.Repositories.OrdersRepository;
import utn.frc.parcial.Repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;
    private OrderDetailsRepository orderDetailsRepository;

    public OrderDetailService(OrdersRepository ordersRepository, ProductsRepository productsRepository, OrderDetailsRepository orderDetailsRepository) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }


    public List<OrderDetailResponseDTO> findAll() {
        return orderDetailsRepository.findAll().stream()
                .map(orderDetail -> new OrderDetailResponseDTO(orderDetail)).toList();
    }


    public Optional<OrderDetailResponseDTO> findById(Long orderId, Long productId) {
        OrderDetail.OrderDetailId orderDetailId = validarYFormarId(orderId, productId);

        Optional<OrderDetail> orderDetail = orderDetailsRepository.findById(orderDetailId);

        System.out.println(orderDetail);
        return orderDetail.isEmpty()
                ? Optional.empty()
                : Optional.of(new OrderDetailResponseDTO(orderDetail.get()));
    }


    public OrderDetailResponseDTO add(CreateOrderDetailDTO createOrderDetailDTO) {
        OrderDetail.OrderDetailId orderDetailId = validarYFormarId(createOrderDetailDTO.getOrderId(), createOrderDetailDTO.getProductId());
        OrderDetail orderDetail = new OrderDetail(createOrderDetailDTO, orderDetailId);
        return new OrderDetailResponseDTO(orderDetailsRepository.save(orderDetail));
    }

    public OrderDetailResponseDTO update(Long orderId, Long productId, UpdateOrderDetailDTO updateOrderDetailDTO) {
        OrderDetail.OrderDetailId orderDetailId = validarYFormarId(orderId, productId);
        OrderDetail orderDetail = orderDetailsRepository.findById(orderDetailId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("OrderDetail %d inexistente", orderId))
        );

        orderDetail.setQuantity(updateOrderDetailDTO.getQuantity());
        orderDetail.setUnitPrice(updateOrderDetailDTO.getUnitPrice());
        orderDetail.setDiscount(updateOrderDetailDTO.getDiscount());


        return new OrderDetailResponseDTO(orderDetailsRepository.save(orderDetail));
    }


    public boolean deleteById(Long orderId, Long productId) {
        OrderDetail.OrderDetailId orderDetailId = validarYFormarId(orderId, productId);
        if (!orderDetailsRepository.existsById(orderDetailId)) {
            return false;
        }

        orderDetailsRepository.deleteById(orderDetailId);
        return true;
    }

    private OrderDetail.OrderDetailId validarYFormarId(Long orderId, Long productId){
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Order %d inexistente", orderId))
        );
        Product product = productsRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product %d inexistente", productId))
        );
        OrderDetail.OrderDetailId orderDetailId = new OrderDetail.OrderDetailId(order, product);
        return orderDetailId;
    }
    


}
