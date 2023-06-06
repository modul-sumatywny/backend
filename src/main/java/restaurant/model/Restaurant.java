package restaurant.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Menu menu;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Table> tables;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Order> orders;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE}, mappedBy = "restaurant")
    private List<Stock> stocks;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Restaurant{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
