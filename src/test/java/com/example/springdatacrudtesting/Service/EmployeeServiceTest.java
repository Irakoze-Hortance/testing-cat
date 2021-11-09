package com.example.springdatacrudtesting.Service;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    public  void returnEmployees(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("Irakoze","Hortance","testingemail@gmail")));
        assertEquals("Irakoze",employeeService.getAllEmployees().get(1).getFirstName());

    }
    @Test
    public void createEmployee(){
        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(new Employee("Izere","Noella","useremail@gmail.com"));
        assertEquals("useremail@gmail.com",employeeService.createEmployee("Izere","Noella","useremail@gmail.com").getEmail());

    }

    @Test
    public  void deleteEmployee(){
        Optional<Employee> employee=employeeRepository.findById(1L);
        employeeService.deleteEmployee(1L);
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    public void updateEmployee(){
        Employee  employee=employeeRepository.findById(2L).get();
        employee.setEmail("testingemail@gmail.com");
        Employee updatedEmployee=employeeRepository.save(employee);
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("testingemail@gmail.com");
    }

    @Test
    public void returnEmployee(){
        Employee employee=new Employee();
        employee.setId(2);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
         Employee expected=employeeService.getEmployeeDetail(employee.getId());
         Assertions.assertThat(expected).isSameAs(employee);
         verify(employeeRepository).findById(employee.getId());
    }
}
