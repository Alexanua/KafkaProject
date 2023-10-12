package Majdi.kafka1.kafka;



import Majdi.kafka1.payload.User;
import Majdi.kafka1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {

    private final UserRepository userRepository;

    @Autowired
    public UserKafkaConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "user", groupId = "userGroup")
    public void consumeUser(User user) {
        System.out.println(user);
        System.out.println("Saving User data to DB!");
        userRepository.save(user);
    }

    //http://localhost:8081/api/v1/kafka/user/byid?id=69
}

