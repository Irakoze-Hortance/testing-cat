package com.example.springdatacrudtesting.controller;

import com.example.springdatacrudtesting.Controller.EmployeeController;
import com.example.springdatacrudtesting.Repository.EmployeeRepository;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import com.example.springdatacrudtesting.utils.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @MockBean
     EmployeeService employeeServiceMock;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_test()  throws  Exception{
        when(employeeServiceMock.createEmployee(any(Employee.class))).thenReturn(new Employee( "denyse", "mutoni", "dmuto"));
        MockHttpServletRequestBuilder request=MockMvcRequestBuilders.post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"denyse\",\"lastName\":\"mutoni\",\"email\":\"dmuto\"}");

        mockMvc.perform(request).andExpect(status().isCreated()).andExpect(content().json("{\"firstName\":\"denyse\",\"lastName\":\"mutoni\",\"email\":\"dmuto\"}"));
    }
    @Test
    public void getListOfEmployeesTest() throws Exception {
        List<Employee> asList = Arrays.asList(new Employee(1, "denyse", "mutoni", "dmuto"),
                new Employee(2, "lolo", "lomu", "mukezwa"));
        when(employeeServiceMock.getAllEmployees()).thenReturn(asList);

        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .get("/employees")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result= (MvcResult) mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"denyse\",\"lastName\":\"mutoni\",\"email\":\"dmuto\"},{\"id\":2,\"firstName\":\"lolo\",\"lastName\":\"lomu\",\"email\":\"mukezwa\"}]"))
                .andReturn();
    }

    @Test
    public  void getById_notfound() throws Exception{
        when(employeeServiceMock.getById(anyInt())).thenReturn(Optional.of(new Employee(2, "Nicole", "Mukundwa", "emailye")));
        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .get("/employees/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }
    @Test
    public void findById() throws Exception {
        when(employeeServiceMock.getById(1)).thenReturn(Optional.of(new Employee(1, "Nicole", "Mukundwa", "emailye")));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/employee/1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }



    @Test
    public void getByOne_404() throws Exception{
        Employee employee=new Employee(1,"Employee1","Employee2","email@test");

        when(employeeServiceMock.getById(employee.getId())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .get("/employees/2")
                .accept(MediaType.APPLICATION_JSON);

                MvcResult result=mockMvc
                        .perform(request)
                        .andExpect(status().isNotFound())
                        .andExpect(content().json("{\"status\":false,\"message\":\"Employee not found\"}"))
                        .andReturn();
    }

    @Test
    public void update_success() throws Exception{
        Employee employee=new Employee(1,"Employee1","Employee2","email@test");
        ResponseEntity.status(HttpStatus.CREATED).body(employee);

        ResponseEntity<?> response=ResponseEntity.status(HttpStatus.CREATED).body(employee);
        when(employeeServiceMock.updateEmployee(1,new Employee("egide","harerimana","email@egide"))).thenReturn(employee);
        when(employeeServiceMock.getById(1)).thenReturn(Optional.of(employee));
        when(employeeServiceMock.save(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .put("/employees/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lastName\": \"No name\"}");

        MvcResult result=mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\":\"Employee1\",\"lastName\":\"Employee2\",\"email\":\"email@test\"}"))
                .andReturn();
    }

    @Test
    public void create_test_duplicateEmail(){
        when(employeeRepository.findByEmail(anyString())).thenReturn(true);
        Exception exception=assertThrows(CustomException.class,()->employeeServiceMock.createEmployee("peace","peace","peaceemail"));
        assertEquals("Employee with this email already exists",exception.getMessage());
    }
    @Test
    public void update_fail() throws Exception{
        Employee employee=new Employee(1,"Employee1","Employee2","email@test");
        ResponseEntity.status(HttpStatus.CREATED).body(employee);

        ResponseEntity<?> response=ResponseEntity.status(HttpStatus.CREATED).body(employee);
        when(employeeServiceMock.updateEmployee(1,new Employee("egide","harerimana","email@egide"))).thenReturn(null);
        //when(employeeServiceMock.getById(1)).thenReturn(Optional.of(employee));
        //when(employeeServiceMock.save(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .put("/employees/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("");

        MvcResult result=mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Employee not found\"}"))
                .andReturn();
    }
}
