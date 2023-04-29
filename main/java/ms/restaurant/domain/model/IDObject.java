package ms.restaurant.domain.model;

import lombok.Data;

@Data
public class IDObject {
    private Long id;

    public IDObject(Long id) {
        this.id = id;
    }
}