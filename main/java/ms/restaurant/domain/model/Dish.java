package ms.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DISHES")
public class Dish {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    @ManyToMany(fetch = FetchType.LAZY ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "dish_product",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

}
