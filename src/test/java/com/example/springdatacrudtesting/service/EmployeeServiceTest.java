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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
