package restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DishProductId implements Serializable {
    @Column(name = "dish_id")
    private Long dish;
    @Column(name = "product_id")
    private Long product;
}
