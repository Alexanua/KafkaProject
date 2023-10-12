package Majdi.kafka1;

import Majdi.kafka1.payload.Admin;
import Majdi.kafka1.payload.Employee;
import Majdi.kafka1.Repository.AdminRepository;
import Majdi.kafka1.Repository.EmployeeRepository;
import Majdi.kafka1.Repository.UserRepository;
import Majdi.kafka1.payload.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    static User testUser;
    static Admin testAdmin;
    static Employee testEmployee;

    @BeforeEach
    void setUp() {
        System.out.println("Before Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Alla test avslutade!");
    }

    @Test
    @Order(1)
    void createUser() {
        User user = new User();
        user.setFirstName("Majdi");
        user.setLastName("Sharif");
        testUser = userRepository.save(user);
        assertNotNull(userRepository.findById(testUser.getId()).get().getFirstName());
        System.out.println(testUser.getId());
    }

    @Test
    @Order(2)
    void updateUser() {
        User fetchedUser = userRepository.findById(testUser.getId()).get();
        assertNotNull(fetchedUser);
        fetchedUser.setFirstName("Niklas1");
        userRepository.save(fetchedUser);
        assertEquals("Niklas1", userRepository.findById(testUser.getId()).get().getFirstName());
    }

    @Test
    @Order(3)
    void createAdmin() {
        Admin admin = new Admin();
        admin.setFirstName("Majdi");
        admin.setLastName("TestLastName");
        admin.setRole("AdminRole");
        testAdmin = adminRepository.save(admin);
        assertNotNull(adminRepository.findById(testAdmin.getId()).get().getFirstName());
        System.out.println(testAdmin.getId());
    }

    @Test
    @Order(4)
    void updateAdmin() {
        Admin fetchedAdmin = adminRepository.findById(testAdmin.getId()).orElse(null);
        assertNotNull(fetchedAdmin);
        fetchedAdmin.setFirstName("Ny Majdi");
        adminRepository.save(fetchedAdmin);
        assertEquals("Ny Majdi", adminRepository.findById(testAdmin.getId()).get().getFirstName());
    }

    @Test
    @Order(5)
    void createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Alin");
        employee.setLastName("TestLastName");
        employee.setDepartment("IT");
        testEmployee = employeeRepository.save(employee);
        assertNotNull(employeeRepository.findById(testEmployee.getId()).get().getFirstName());
        System.out.println(testEmployee.getId());
    }

    @Test
    @Order(6)
    void updateEmployee() {
        Employee fetchedEmployee = employeeRepository.findById(testEmployee.getId()).get();
        assertNotNull(fetchedEmployee);
        fetchedEmployee.setFirstName("UpdatedName");
        employeeRepository.save(fetchedEmployee);
        assertEquals("UpdatedName", employeeRepository.findById(testEmployee.getId()).get().getFirstName());
    }


}
