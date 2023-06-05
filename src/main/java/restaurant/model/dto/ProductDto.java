package restaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Name of product cannot be blank")
    private String name;

    @NotBlank(message = "EAN number cannot be blank")
    private String ean;

    private MeasurementUnit measurementUnit;

}
