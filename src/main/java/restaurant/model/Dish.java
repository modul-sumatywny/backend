package restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Table;

import static jakarta.persistence.CascadeType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Dishes")
public class Dish implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "dish_id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Integer price;

    @ManyToMany(mappedBy = "dishes")
    private List<Menu> menus;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE},mappedBy = "dish")
    private List<DishProduct> dishProducts;

    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dish{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
