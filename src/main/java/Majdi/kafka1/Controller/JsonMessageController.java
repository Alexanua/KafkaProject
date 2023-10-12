package Majdi.kafka1.Controller;

import Majdi.kafka1.payload.Admin;
import Majdi.kafka1.payload.Employee;
import Majdi.kafka1.payload.User;
import Majdi.kafka1.kafka.JsonKafkaProducer;
import Majdi.kafka1.Repository.AdminRepository;
import Majdi.kafka1.Repository.UserRepository; // Import UserRepository
import Majdi.kafka1.Repository.EmployeeRepository; // Import EmployeeRepository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {

    private final JsonKafkaProducer kafkaProducer;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository; // Define userRepository
    private final EmployeeRepository employeeRepository; // Define employeeRepository

    // Mocking repositories for the sake of this example
    private final List<User> users = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();

    @Autowired
    public JsonMessageController(JsonKafkaProducer kafkaProducer, AdminRepository adminRepository, UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.kafkaProducer = kafkaProducer;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository; // Inject employeeRepository
    }

    // User endpoints
    @PostMapping("/user")
    public ResponseEntity<String> publishUser(@RequestBody User user) {
        kafkaProducer.sendMessage(user);
        users.add(user);
        return ResponseEntity.ok("User JSON Message sent to Kafka Topic");
    }

    @GetMapping("/user/byid")
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        Optional<User> userOptional = userRepository.findById(id); // Use the injected userRepository
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Admin endpoints
    @PostMapping("/admin")
    public ResponseEntity<String> publishAdmin(@RequestBody Admin admin) {
        kafkaProducer.sendMessage(admin);
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin JSON Message sent to Kafka Topic");
    }

    @GetMapping("/admin/byid")
    public ResponseEntity<Admin> getAdminById(@RequestParam Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            return ResponseEntity.ok(adminOptional.get());
        } else {
            return ResponseEntity.status(404).body(new Admin());  // Return an empty Admin object on not found.
        }
    }

    // Employee endpoints
    @PostMapping("/employee")
    public ResponseEntity<String> publishEmployee(@RequestBody Employee employee) {
        kafkaProducer.sendMessage(employee);
        employees.add(employee);  // Consider saving to a real database
        return ResponseEntity.ok("Employee JSON Message sent to Kafka Topic");
    }

    @GetMapping("/employee/byid")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id); // Use the correct repository
        if (employeeOptional.isPresent()) {
            return ResponseEntity.ok(employeeOptional.get());
        } else {
            return ResponseEntity.status(404).body(new Employee());  // Return an empty Employee object on not found.
        }
    }
}
