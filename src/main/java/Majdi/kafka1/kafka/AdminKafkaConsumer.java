package Majdi.kafka1.kafka;

import Majdi.kafka1.Repository.AdminRepository;
import Majdi.kafka1.payload.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AdminKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AdminKafkaConsumer.class);

    private final AdminRepository adminRepository;

    @Autowired
    public AdminKafkaConsumer(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @KafkaListener(topics = "admin", groupId = "adminGroup")
    public void consumeAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
            logger.info("Successfully saved Admin: {}", admin);
        } catch (Exception e) {
            logger.error("Error while saving Admin: {}", admin, e);
        }
    }

    //http://localhost:8081/api/v1/kafka/admin/byid?id=76
}
