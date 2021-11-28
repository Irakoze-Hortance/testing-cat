package com.example.springdatacrudtesting.services;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.springdatacrudtesting.utils.CustomException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
        @Autowired
        EmployeeRepository employeeRepository;

        public List<Employee> getAllEmployees(){
            return  employeeRepository.findAll();
        }


        public Employee createEmployee(Employee employee){
            return employeeRepository.save(employee);
        }

    public Employee createEmployee(String firstName,String lastName, String email){
            Employee employee=new Employee(firstName,lastName,email);
            return  employeeRepository.save(employee);
        }
        public void deleteEmployee(int id){
            employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id" + id));
            employeeRepository.deleteById(id);
        }

        public  Employee updateEmployee(int id, Employee employee){
            employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id " +id));
            employee.setId(id);
            return employeeRepository.save(employee);
        }

        public Employee getEmployeeDetail(int id){
            return  employeeRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Employee not found with id" +id));
        }

        public Optional<Employee> getById(int id){
           return employeeRepository.findById(id);

        }


    public Employee save(Employee employee) {
            return employeeRepository.save(employee);
    }


    public Employee getByEmail(String email){
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Employee with this email not found", HttpStatus.NOT_FOUND));
        return employee;
    }


}
