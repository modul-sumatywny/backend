package ms.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;


    //    @OneToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
        CascadeType.REFRESH})
    @JoinTable(
        name = "ORDER_DISH",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public enum OrderStatus {
        PLACED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}

