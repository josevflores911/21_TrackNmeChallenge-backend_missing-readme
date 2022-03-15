package com.tracknme.firstTry.service;

import com.tracknme.firstTry.entities.Employee;
import com.tracknme.firstTry.repository.EmployeeRepository;
import com.tracknme.firstTry.services.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    public Employee employeeCreated(){
        //UUID fakeId = UUID.fromString("05821d4e-dfc0-4fc5-b01e-70b3a40d212d");
        Employee employee = Employee.builder()

                .age(20)
                .name("Antonio")
                .gender("Gender.MALE")
                .cep(20551070)
                .adress("Some Adress")
                .state("Some State")
                .street("Some Street")
                .city("Some City").build();

        return employee;
    }

    @Test
    public void employeeFindByIdServiceTest(){

        Mockito.when(employeeRepository.findById(employeeCreated().getId()))
                .thenReturn(Optional.ofNullable(employeeCreated()));

        Employee found = employeeService.getEmployeeById(employeeCreated().getId());

        Assertions.assertNotNull(found);

    }

    @Test
    public void employeeUpdateServiceTest(){
        Mockito.when(employeeRepository.findById(employeeCreated().getId()))
                .thenReturn(Optional.ofNullable(employeeCreated()));

        Employee newEmployee = Employee.builder()
                .age(20)
                .name("carlos")
                .gender("Gender.MALE")
                .cep(20551070)
                .adress("update")
                .state("update")
                .street("update")
                .city("update").build();


        Mockito.when(employeeRepository.findById(newEmployee.getId()))
                .thenReturn(Optional.of(newEmployee));

        Boolean updated = employeeService.updateEmployee(newEmployee);

        //testes
        Assertions.assertNotNull(newEmployee);

        Assertions.assertNotEquals(newEmployee,employeeCreated().getName());

        Assertions.assertTrue(updated);
    }


    @Test
    public void employeeDeleteServiceTest(){
        Mockito.when(employeeRepository.findById(employeeCreated().getId()))
                .thenReturn(Optional.ofNullable(employeeCreated()));

        Boolean deleted = employeeService.deleteById(employeeCreated().getId());

        Assertions.assertFalse(deleted);
    }

}
