package com.example.springdatacrudtesting.services;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
        @Autowired
        EmployeeRepository employeeRepository;

        public List<Employee> getAllEmployees(){
            return  employeeRepository.findAll();
        }

        public Employee createEmployee(String firstName,String lastName, String email){
            Employee employee=new Employee(firstName,lastName,email);
            return  employeeRepository.save(employee);
        }
        public void deleteEmployee(Long id){
            employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id" + id));
            employeeRepository.deleteById(id);
        }

        public  Employee updateEmployee(Long id, Employee employee){
            employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id " +id));
            employee.setId(id);
            return employeeRepository.save(employee);
        }
        public Employee getEmployeeDetail(Long id){
            return  employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id" +id));
        }

}
