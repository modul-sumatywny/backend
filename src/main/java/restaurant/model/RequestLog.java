package restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "request_logs")
public class RequestLog {

    @Id
    private String id;
    private String url;
    private String method;
    private String request;
    private String response;
}