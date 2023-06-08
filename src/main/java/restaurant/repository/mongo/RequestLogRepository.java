package restaurant.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import restaurant.model.RequestLog;
@Repository
public interface RequestLogRepository extends MongoRepository<RequestLog, String> {
}
