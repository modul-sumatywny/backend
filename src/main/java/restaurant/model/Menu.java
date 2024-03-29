package restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Menus")
public class Menu implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id", nullable = false)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE},mappedBy = "menu")
    @JsonManagedReference
    private List<Restaurant> restaurants;

    @ManyToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE})
    @JoinTable(
            name = "menu_dish",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Dish> dishes;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", restaurants=").append(restaurants);
        sb.append(", dishes=").append(dishes);
        sb.append('}');
        return sb.toString();
    }
}
