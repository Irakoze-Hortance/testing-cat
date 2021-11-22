package com.example.springdatacrudtesting.controller;

import com.example.springdatacrudtesting.Controller.EmployeeController;
import com.example.springdatacrudtesting.entity.Employee;
import com.example.springdatacrudtesting.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeServiceMock;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getListOfEmployeesTest() throws Exception{
       List<Employee> asList= Arrays.asList(new Employee(1,"denyse","mutoni","dmuto"),
               new Employee(2,"lolo","lomu","mukezwa"));
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
    public void getByOne_404() throws Exception{
        Employee employee=new Employee(1,"Employee1","Employee2","email@test");

        when(employeeServiceMock.getById(employee.getId())).thenReturn(employee);

        MockHttpServletRequestBuilder request=MockMvcRequestBuilders
                .get("/employee/2")
                .accept(MediaType.APPLICATION_JSON);

                MvcResult result=mockMvc
                        .perform(request)
                        .andExpect(status().isNotFound())
                        .andExpect(content().json("{\"status\":false,\"message\":\"Employee not found\"}"))
                        .andReturn();
    }
}
