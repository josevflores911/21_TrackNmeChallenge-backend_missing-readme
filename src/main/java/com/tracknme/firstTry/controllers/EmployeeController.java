package com.tracknme.firstTry.controllers;

import com.tracknme.firstTry.entities.CepDetailsDto;
import com.tracknme.firstTry.entities.Employee;
import com.tracknme.firstTry.entities.EmployeeDto;
import com.tracknme.firstTry.repository.EmployeeRepository;
import com.tracknme.firstTry.services.CepFinderService;
import com.tracknme.firstTry.services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/v1/employee")
@CrossOrigin(origins="*")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final CepFinderService cepFinderService;

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, CepFinderService cepFinderService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.cepFinderService = cepFinderService;
        this.employeeRepository = employeeRepository;
    }

    //create a new employee
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="create a new employee in DB")
    public ResponseEntity<Boolean> createEmployee(@RequestBody EmployeeDto employee) {
        Boolean response = employeeService.CreateEmployee(employee);
        return ResponseEntity.ok().body(response);
    }

    //list all employees
    @GetMapping(path = "/Employees/{offset}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees(@PathVariable int offset, @PathVariable int pageSize) {
        List<Employee> response = employeeService.getAllEmployees(offset, pageSize);
        return ResponseEntity.ok().body(response);
    }


    //return employee with id
    @GetMapping(value = "/EmployeeId/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id")  UUID id ) {
        Employee response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok().body(response);
    }

    //return employee with cep
    @GetMapping(value = "/EmployeeCep/{cep}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployeeByCep(@PathVariable("cep")  Integer cep) {
        List<Employee> response = employeeService.getEmployeeByCep(cep);
        return ResponseEntity.ok().body(response);
    }

    //update element with id
    @PutMapping(value = "/update" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateEmployeeById(@RequestBody Employee employee){
        return ResponseEntity.ok().body(employeeService.updateEmployee(employee));
    }

    //delete  employee with id
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable("id") UUID id ) {

        Boolean response = employeeService.deleteById(id);
        return ResponseEntity.ok().body(response);
    }

    //patch
    @PatchMapping(path = "/{id}")
    public ResponseEntity<Boolean> saveManager(@PathVariable("id") UUID id, @RequestBody Map<Object,Object> fields){
        Employee employee = employeeService.getEmployeeById(id);

        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Employee.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,employee,value);
        });

        return ResponseEntity.ok().body(employeeService.updateEmployee(employee));
    }




    //cep finder
    @GetMapping(path = "{cepNumber}/json/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CepDetailsDto> getCep(@PathVariable Integer cepNumber){
        CepDetailsDto response = cepFinderService.findCep(cepNumber);
        return ResponseEntity.ok().body(response);
    }


}
