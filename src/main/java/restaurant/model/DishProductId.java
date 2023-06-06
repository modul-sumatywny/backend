package restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DishProductId implements Serializable {
    @Column(name = "dish_id")
    private Long dish;
    @Column(name = "product_id")
    private Long product;
}
