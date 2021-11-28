package com.example.springdatacrudtesting.Controller;

import com.example.springdatacrudtesting.entity.Employee;
import  com.example.springdatacrudtesting.utils.Formatter;
import com.example.springdatacrudtesting.services.EmployeeService;
import com.example.springdatacrudtesting.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Formatter.*;


@RestController
public class EmployeeController {

    @Autowired
    EmployeeService  employeeService;


    @GetMapping("/employees")
    public List<Employee> getAll(){
        return  employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable("id") int id){
        Optional<Employee> employeeData=employeeService.getById(id);
        return employeeData.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/employee")
   public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        return Formatter.send(employeeService.createEmployee(employee),HttpStatus.CREATED);
    }
    @PutMapping ("/employees/{id}")
    public ResponseEntity<?> updateEmployee_fail(@PathVariable("id") int id, @RequestBody Employee employee){
        return  Formatter.send(employeeService.updateEmployee(id,employee),HttpStatus.ACCEPTED);
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id){
        Optional<Employee> employeeData=employeeService.getById(id);
        if(employeeData.isPresent()){
            Employee _employee=employeeData.get();
            _employee.setEmail(_employee.getEmail());
            _employee.setFirstName(_employee.getFirstName());
            _employee.setLastName(_employee.getLastName());
            return new ResponseEntity<>(employeeService.save(_employee),HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") int id){
        try{
            employeeService.deleteEmployee(id);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/{id}")
    public  ResponseEntity<?> getById(@PathVariable(name="id")int id){
        Optional<Employee> employee=employeeService.getById(id);
        if(employee.isPresent()){
            return  ResponseEntity.ok(employee);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Employee not found"));
    }
    @GetMapping("/byEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        return Formatter.ok(employeeService.getByEmail(email));
    }

}
