package restaurant.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Stock")
public class Stock implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column
    private Integer stock;

    @Column
    private Boolean isEnabled = false;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stock=" + stock +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
