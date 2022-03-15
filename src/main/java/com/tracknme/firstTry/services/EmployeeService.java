package com.tracknme.firstTry.services;

import com.tracknme.firstTry.entities.*;
import com.tracknme.firstTry.exceptions.ResourceNotFoundException;
import com.tracknme.firstTry.repository.CepRepository;
import com.tracknme.firstTry.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final CepRepository cepRepository;

    private final CepFinderService cepFinderService;


    public EmployeeService(EmployeeRepository employeeRepository, CepRepository cepRepository, CepFinderService cepFinderService) {
        this.employeeRepository = employeeRepository;
        this.cepRepository = cepRepository;
        this.cepFinderService = cepFinderService;
    }

    public Boolean CreateEmployee(EmployeeDto employee){

        Employee newEmployee = new Employee();

        //empty name,
        if(employee.getName().equals("")){
            throw new IllegalStateException("please put name");
        }else{
            newEmployee.setName(employee.getName());
        }

        //gender could be a enum
        if(!employee.getGender().equals(Gender.MALE) || !employee.getGender().equals(Gender.FEMALE)){
            newEmployee.setGender("NA");
        }

        //age smaller than 18
        if(employee.getAge()<18){
            throw new IllegalStateException("employee dont qualify");
        }else{
            newEmployee.setAge(employee.getAge());
        }

        //empty cep with message format 8 numbers and not null
       if(!cepFinderService.findCep(employee.getCep()).getCep().isEmpty()){
            if(employee.getCep()<10000000 || employee.getCep()>100000000){
                throw new IllegalStateException("cep must have 8 valids digits");
            }else{

                newEmployee.setGender(employee.getGender());

                newEmployee.setCep(employee.getCep());
                newEmployee.setState(cepFinderService.findCep(employee.getCep()).getLocalidade());
                newEmployee.setStreet(cepFinderService.findCep(employee.getCep()).getLogradouro());
                newEmployee.setCity(cepFinderService.findCep(employee.getCep()).getUf());

            }
       }

        if(!cepRepository.findById(employee.getCep()).isPresent()){

            CepDetails newCep = new CepDetails();

            newCep.setCep(employee.getCep());
            newCep.setBairro(cepFinderService.findCep(employee.getCep()).getBairro());
            newCep.setComplemento(cepFinderService.findCep(employee.getCep()).getComplemento());
            newCep.setLocalidade(cepFinderService.findCep(employee.getCep()).getLocalidade());
            newCep.setLogradouro(cepFinderService.findCep(employee.getCep()).getLogradouro());
            newCep.setDdd(cepFinderService.findCep(employee.getCep()).getDdd());
            newCep.setGia(cepFinderService.findCep(employee.getCep()).getGia());
            newCep.setSiafi(cepFinderService.findCep(employee.getCep()).getSiafi());
            newCep.setUf(cepFinderService.findCep(employee.getCep()).getUf());
            newCep.setIbge(cepFinderService.findCep(employee.getCep()).getIbge());

            cepRepository.save(newCep);
        }
        employeeRepository.save(newEmployee);

        if(!employeeRepository.findById(newEmployee.getId()).isPresent()){
            return false;
        }else{
            return true;
        }
    }

    public List<Employee> getAllEmployees(int offset, int pageSize) {

        Pageable page = PageRequest.of(offset,pageSize);

        return employeeRepository.findAll(page).stream().collect(Collectors.toList());
    }

    public Employee getEmployeeById(UUID id) {
        Employee employee =employeeRepository.findById(id).get();

        if(!employeeRepository.findById(id).isPresent()){
            throw new IllegalStateException("no employee with id");
        }

        return employee;
    }

    public List<Employee> getEmployeeByCep(Integer employeeCep){

        List<Employee> employees = new ArrayList<>();

        for(Employee el:employeeRepository.findByCep(employeeCep)){
            employees.add(el);
        }

        return employees;
    }

    //update Employee
    public Boolean updateEmployee(Employee employee){
        //----overwriting---
        Employee updatedEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id: "+ employee.getId()));

        updatedEmployee.setName(employee.getName());
        updatedEmployee.setAge(employee.getAge());
        updatedEmployee.setCep(employee.getCep());
        updatedEmployee.setGender(employee.getGender());
        updatedEmployee.setAdress(employee.getAdress());
        updatedEmployee.setStreet(employee.getStreet());
        updatedEmployee.setCity(employee.getCity());
        updatedEmployee.setState(employee.getState());

        employeeRepository.save(updatedEmployee);
        //employeeRepository.save(employee);

        return employeeRepository.findById(employee.getId()).isPresent();
    }

    //employee  delete
    public Boolean deleteById(UUID id){
        employeeRepository.deleteById(id);

        return !employeeRepository.findById(id).isPresent();
    }

}
