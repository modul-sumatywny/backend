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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id", nullable = false)
    private List<Product> products = new ArrayList<>();
}
