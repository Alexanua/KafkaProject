package Majdi.kafka1.kafka;

import Majdi.kafka1.Repository.EmployeeRepository;
import Majdi.kafka1.payload.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaConsumer {

    @Autowired
    private EmployeeRepository employeeRepository;

    @KafkaListener(topics = "employee", groupId = "employeeGroup")
    public void consumeEmployee(Employee employee) {
        try {
            System.out.println("Received Employee: " + employee);
            employeeRepository.save(employee);
        } catch (Exception e) {
            System.err.println("Error consuming Employee message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //http://localhost:8081/api/v1/kafka/employee/byid?id=96
}
