package Majdi.kafka1.kafka;

import Majdi.kafka1.Repository.AdminRepository;
import Majdi.kafka1.payload.Admin;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumerDb {

    private final AdminRepository adminRepository;

    public JsonKafkaConsumerDb(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // @KafkaListener(topics = "admin", groupId = "myGroup")
    // Den här metoden är nu en vanlig metod och kommer inte att köras automatiskt när ett meddelande kommer till "admin"-ämnet.
    public void writeToDb(Admin admin) {
        System.out.println(admin);
        System.out.println("Sending data to DB!");
        adminRepository.save(admin);
    }
}
