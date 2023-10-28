package utn.frc.parcial.Api.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.parcial.Entities.Order;
import utn.frc.parcial.Entities.OrderDetail;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreadoDto {
    private Order order;

    private OrderDetail[] listaDedetaklles;

    public PedidoCreadoDto(Order ordenCreada, List<OrderDetail> listaDeDetalles) {
    }
}
