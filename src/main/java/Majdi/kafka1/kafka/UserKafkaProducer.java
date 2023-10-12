package Majdi.kafka1.kafka;

import Majdi.kafka1.payload.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public UserKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(User user) {
        kafkaTemplate.send("user", user); // "user" is the topic name
    }


    //http://localhost:8081/api/v1/kafka/user
/*
    {

        "firstName": "John",
            "lastName": "Doe"
    }


 */
}
