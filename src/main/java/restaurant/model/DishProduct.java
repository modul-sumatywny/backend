package restaurant.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Data
@Table(name = "dish_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishProduct implements ModelEntity<DishProductId> {

    @EmbeddedId
    DishProductId id;
    @ManyToOne()
    @MapsId("dish")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne()
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}
