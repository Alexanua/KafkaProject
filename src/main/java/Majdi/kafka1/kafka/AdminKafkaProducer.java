

package Majdi.kafka1.kafka;

import Majdi.kafka1.payload.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(AdminKafkaProducer.class);

    private final KafkaTemplate<String, Admin> kafkaTemplate;

    @Autowired
    public AdminKafkaProducer(KafkaTemplate<String, Admin> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdmin(Admin admin) {
        try {
            kafkaTemplate.send("admin", admin);
            logger.info("Successfully sent Admin: {}", admin);
        } catch (Exception e) {
            logger.error("Error while sending Admin: {}", admin, e);
        }
    }
}

    //http://localhost:8081/api/v1/kafka/admin

    /*
    {
    "firstName": "Marah",
    "lastName": "aldin",
    "name": "Marah aldin",
    "role": "IT Manager"
}

     */
