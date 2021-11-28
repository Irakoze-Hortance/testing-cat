package com.example.springdatacrudtesting.service;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public  void getAllEmployees(){
        when(employeeRepositoryMock.findAll()).thenReturn(Arrays.asList(new Employee(1,"Irakoze","hortance","email@email"),
                new Employee(2,"omuana","same","newEmail")));
        assertEquals("email@email",employeeService.getAllEmployees().get(0).getEmail());
    }
    @Test
    public void findById(){
        when(employeeRepositoryMock.findById(anyInt())).thenReturn(Optional.of(new Employee(1,"Nicole","Mukundwa","muku")));
        assertEquals("Mukundwa",employeeService.getById(1).get().getLastName());
    }
    @Test
    public void updateTest(){
        when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(new Employee(1,"omuana","same","newEmail"));
        when(employeeRepositoryMock.findById(anyInt())).thenReturn(Optional.of( new Employee(1,"omuana","same","newEmail")));

        Employee updated=employeeService.updateEmployee(1,new Employee());
        assertEquals("same",updated.getLastName());
    }

    @Test
    public void createTest(){
        when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(new  Employee(1,"prep","exam","newEmail"));
        assertEquals("prep",employeeService.createEmployee(new Employee()).getFirstName());
    }
}
