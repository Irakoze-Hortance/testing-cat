package com.example.springdatacrudtesting.services;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        public Employee getById(int id){
            Optional<Employee> findByID=employeeRepository.findById(id);
            if(findByID.isPresent()){
                Employee employee=findByID.get();
                return  employee;
            }
            return null;
        }

}
