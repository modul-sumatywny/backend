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
    @Column(name = "dish_id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Integer price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id")
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
}
