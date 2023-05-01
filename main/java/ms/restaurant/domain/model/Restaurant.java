package ms.restaurant.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer phoneNumber;

    @Column
    private String address;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
