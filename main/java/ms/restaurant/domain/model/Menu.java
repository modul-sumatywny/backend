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
@Table(name = "MENUS")
public class Menu {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id", nullable = false)
    private Long id;

    @Column
    private String name;

//    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
//   // @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
//    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private List<Dish> dishes = new ArrayList<>();

//    @OneToOne(mappedBy = "menu")
//    @JoinColumn(name = "restaurant_id", nullable = false)
//    private Restaurant restaurant;
}
