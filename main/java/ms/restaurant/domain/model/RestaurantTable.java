package ms.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TABLES")
public class RestaurantTable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "table_id", nullable = false)
    private Long id;

    @Column
    private Integer tableNumber;

    @Column
    private Integer numberOfSeats;
}
