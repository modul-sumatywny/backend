package restaurant.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dish_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishProduct {

    @EmbeddedId
    DishProductId id;
    @ManyToOne
    @MapsId("dish_id")
    @JoinColumn(name = "dish_id")
    private Dish dish;


    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}
