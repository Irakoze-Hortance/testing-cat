package com.example.springdatacrudtesting.Controller;

import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class EmployeeController {

    @Autowired
    EmployeeService  employeeService;
    public EmployeeRepository  employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAll(){
        return  employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable("id") long id){
        Optional<Employee> employeeData=employeeRepository.findById(id);
        return employeeData.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/employee")
   public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee.getFirstName(),employee.getLastName(),employee.getEmail());
    }
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id){
        Optional<Employee> employeeData=employeeRepository.findById(id);
        if(employeeData.isPresent()){
            Employee _employee=employeeData.get();
            _employee.setEmail(_employee.getEmail());
            _employee.setFirstName(_employee.getFirstName());
            _employee.setLastName(_employee.getLastName());
            return new ResponseEntity<>(employeeRepository.save(_employee),HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") long id){
        try{
            employeeRepository.deleteById(id);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
