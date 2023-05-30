package restaurant.model;


import jakarta.persistence.*;
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
@jakarta.persistence.Table(name = "Restaurants")
public class Restaurant implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurant_id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE})
    @JoinColumn(name = "restaurant_id")
    private List<Table> tables;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE})
    @JoinColumn(name = "restaurant_id")
    private List<Order> orders;
}
