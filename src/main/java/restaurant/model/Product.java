package restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Products")
public class Product implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column
    private String ean;

    @Column
    private String name;

    @JsonIgnore
    @Column
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;

    @ManyToMany(mappedBy = "products")
    private List<Dish> dishes;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "product")
    private List<Stock> stocks;
}
