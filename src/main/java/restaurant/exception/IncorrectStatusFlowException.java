package restaurant.exception;

import lombok.Getter;
import restaurant.model.ModelEntity;

@Getter
public class IncorrectStatusFlowException extends RuntimeException {

    private static final String messageTemplate = "Incorrect status flow. Status %s to status %s is incorrect!";

    public <T extends ModelEntity<?>> IncorrectStatusFlowException(Object paramValue, Object paramValue2) {
        super(String.format(messageTemplate, paramValue, paramValue2));

    }
}
