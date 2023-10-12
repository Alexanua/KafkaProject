package Majdi.kafka1.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeKafkaProducer.class);

    // Constructor to inject the KafkaTemplate
    public EmployeeKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send a message to the 'employeeTopic' Kafka topic.
     *
     * @param message the message to be sent
     */
    public void sendMessageToEmployeeTopic(String message) {
        kafkaTemplate.send("employee", message);
        LOGGER.info(String.format("Message sent to employee topic: %s", message));
    }
}

/*
http://localhost:8081/api/v1/kafka/employee

{
    "firstName": "MAJDI",
    "lastName": "SHARIF",
    "name": "MAJDI SHARIF",
    "department": "IT"
}

 */
