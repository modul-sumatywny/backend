package restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column
    private boolean isEnabled = true;

    @Column
    private Role role = Role.CLIENT;

    @Column
    private String password;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE},mappedBy = "account")
    @JsonManagedReference
    private List<Reservation> reservations;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE},mappedBy = "account")
    @JsonManagedReference
    private List<Order> orders;


    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
//        this.id = UUID.randomUUID();
        this.email = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
