package Majdi.kafka1.kafka;

import Majdi.kafka1.payload.Admin;
import Majdi.kafka1.payload.Employee;
import Majdi.kafka1.payload.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate; // Notera Object här

    // Individual topics for each entity type
    private static final String USER_TOPIC = "user";
    private static final String ADMIN_TOPIC = "admin";
    private static final String EMPLOYEE_TOPIC = "employee";

    public JsonKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User user) {
        kafkaTemplate.send(USER_TOPIC, user);
    }

    public void sendMessage(Admin admin) {
        kafkaTemplate.send(ADMIN_TOPIC, admin);
    }

    public void sendMessage(Employee employee) {
        kafkaTemplate.send(EMPLOYEE_TOPIC, employee);
    }
}
