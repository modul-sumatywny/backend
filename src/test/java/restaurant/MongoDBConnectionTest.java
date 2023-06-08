package restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MongoDBConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDBConnection() {
        boolean isConnected = mongoTemplate.getDb().getName() != null;
        assertTrue(isConnected, "Connection to MongoDB failed");
    }
}
