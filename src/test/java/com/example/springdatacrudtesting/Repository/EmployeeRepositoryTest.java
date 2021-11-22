package com.example.springdatacrudtesting.Repository;

import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryTest {
    @Autowired
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @Order(1)
    @Rollback(value=false)
    public void saveEmployeeTest(){
        Employee  employee=Employee.builder()
                .firstName("Irakoze")
                .lastName("Hortance")
                .email("useremail@gmail.com")
                .build();
        employeeRepository.save(employee);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public  void getEmployeesTest(){
        Employee employee=employeeRepository.findById(1).get();
        Assertions.assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfEmployeesTest(){
        List<Employee> employees=employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value=false)
    public void updateEmployeeTest(){
        Employee  employee=employeeRepository.findById(1).get();
        employee.setEmail("testingemail@gmail.com");
        Employee updatedEmployee=employeeRepository.save(employee);
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("testingemail@gmail.com");
    }
    @Test
    @Order(5)
    public void deleteEmployeeTest(){
        Employee employee=employeeRepository.findById(1).get();
        employeeRepository.delete(employee);
        Employee employee1=new Employee();
        employee1.setFirstName("Irakoze");
        employee1.setLastName("Hortance");
        employee1.setEmail("testingemail@gmail.com");
        Optional<Employee> optionalEmployee=employeeRepository.findByEmail("testingemail@gmail.com");

        if(optionalEmployee.isPresent()){
            employee1=optionalEmployee.get();
        }
        Assertions.assertThat(employee1).isNotNull();
    }

}
