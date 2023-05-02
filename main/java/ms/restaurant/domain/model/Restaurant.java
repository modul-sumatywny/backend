package ms.restaurant.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurant_id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer phoneNumber;

    @Column
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<RestaurantTable> restaurantTables = new ArrayList<>();
}
