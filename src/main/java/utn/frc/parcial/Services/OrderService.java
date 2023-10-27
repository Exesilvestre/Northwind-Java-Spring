package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateOrderDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.ResponseDto.OrderDetailResponseDTO;
import utn.frc.parcial.Api.ResponseDto.OrderRepsonseDTO;
import utn.frc.parcial.Entities.*;
import utn.frc.parcial.Repositories.CustomersRepository;
import utn.frc.parcial.Repositories.EmployeesRepository;
import utn.frc.parcial.Repositories.OrderDetailsRepository;
import utn.frc.parcial.Repositories.OrdersRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrdersRepository ordersRepository;
    private CustomersRepository customersRepository;
    private EmployeesRepository employeesRepository;

    private OrderDetailsRepository orderDetailsRepository;


    public OrderService(OrdersRepository ordersRepository, CustomersRepository customersRepository, EmployeesRepository employeesRepository, OrderDetailsRepository orderDetailsRepository) {
        this.ordersRepository = ordersRepository;
        this.customersRepository = customersRepository;
        this.employeesRepository = employeesRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderRepsonseDTO> findAll() {
        return ordersRepository.findAll().stream()
                .map(product -> new OrderRepsonseDTO(product)).toList();
    }


    public Optional<OrderRepsonseDTO> findById(Long orderId) {
        Optional<Order> order = ordersRepository.findById(orderId);

        return order.isEmpty()
                ? Optional.empty()
                : Optional.of(new OrderRepsonseDTO(order.get()));
    }


    public OrderRepsonseDTO add(CreateOrderDTO createOrderDTO) {
        // Recupera el Customer de la base de datos utilizando el ID del DTO
        Customer customer = recuperarCustomer(createOrderDTO.getCustomerID());
        // Recupera la employee de la base de datos utilizando el ID del DTO

        Employee employee = recuperarElEmployee(createOrderDTO.getEmployeeID());

        Order order = new Order(createOrderDTO, customer, employee);
        return new OrderRepsonseDTO(ordersRepository.save(order));
    }

    public OrderRepsonseDTO update(Long orderId, UpdateOrderDTO updateOrderDTO) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Supplier %d inexistente", orderId))
        );
        Customer customer = recuperarCustomer(updateOrderDTO.getCustomerID());

        Employee employee = recuperarElEmployee(updateOrderDTO.getEmployeeID());

        return new OrderRepsonseDTO(ordersRepository.save(order.update(updateOrderDTO,customer,employee)));
    }


    public boolean deleteById(Long orderId) {
        if (!ordersRepository.existsById(orderId)) {
            return false;
        }

        ordersRepository.deleteById(orderId);
        return true;
    }

    private Customer recuperarCustomer(String id){
        // Recupera el Customer de la base de datos utilizando el ID del DTO
        return customersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Customer %s inexistente", id)));
    }

    private  Employee recuperarElEmployee(Long id){
        // Recupera el employee de la base de datos utilizando el ID del DTO

        return employeesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee %d inexistente", id)));

    }

    public List<OrderDetailResponseDTO> getDetailsByOrderId(Long orderId) {
        // Encuentra la orden por su ID
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order %d inexistente", orderId)));

        // Obtiene los detalles de la orden usando el ID de la orden
        List<OrderDetail> orderDetails = orderDetailsRepository.findByOrderId(order);
        List<OrderDetailResponseDTO> orderDetailResponseDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailResponseDTO responseDTO = new OrderDetailResponseDTO();
            responseDTO.setOrderId(orderDetail.getId().getOrder().getId());
            responseDTO.setProductId(orderDetail.getId().getProduct().getId());
            responseDTO.setUnitPrice(orderDetail.getUnitPrice());
            responseDTO.setQuantity(orderDetail.getQuantity());
            responseDTO.setDiscount(orderDetail.getDiscount());
            // Agrega el objeto OrderDetailResponseDTO a la lista
            orderDetailResponseDTOList.add(responseDTO);
        }


        return orderDetailResponseDTOList;
    }

}
