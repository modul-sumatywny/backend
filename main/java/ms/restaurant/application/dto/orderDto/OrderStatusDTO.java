package ms.restaurant.application.dto.orderDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {

    @NotNull(message = "Order status cannot be null")
//    @EnumValidator(enumClass = Order.OrderStatus.class, message = "Invalid order status") // tu jakies dziwne operacje stworzyc zeby walidowalo
    private String orderStatus;
}
