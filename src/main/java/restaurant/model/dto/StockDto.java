package restaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.Product;
import restaurant.model.Restaurant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {

    private Long id;

    @JsonBackReference
    private Product product;

    private Long restaurantId;

    private Integer stock;

    private Boolean isEnabled;

}
