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

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "product")
    private List<DishProduct> dishProducts;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "product")
    private List<Stock> stocks;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", ean='").append(ean).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", measurementUnit=").append(measurementUnit);
        sb.append('}');
        return sb.toString();
    }
}
