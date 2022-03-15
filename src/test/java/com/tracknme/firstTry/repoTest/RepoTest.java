package com.tracknme.firstTry.repoTest;

import com.tracknme.firstTry.entities.Employee;
import com.tracknme.firstTry.repository.CepRepository;
import com.tracknme.firstTry.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;


@DataJpaTest
@ActiveProfiles("test")
public class RepoTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CepRepository cepRepository;

    @Test
    public void saveEmployeeTest(){

        UUID fakeId = UUID.fromString("5e93b3d9-702a-46a7-98ed-add123126875");

        Employee employee = Employee.builder()
                .age(20)
                .name("Antonio")
                .gender("Gender.MALE")
                .cep(20551070)
                .adress("Some Adress")
                .state("Some State")
                .street("Some Street")
                .city("Some City").build();

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(employee.getAge()).isGreaterThan(10);

    }

    @Test
    public void getEmployeeTest(){

        Employee employee = Employee.builder()
                .age(20)
                .name("Antonio")
                .gender("Gender.MALE")
                .cep(20551070)
                .adress("Some Adress")
                .state("Some State")
                .street("Some Street")
                .city("Some City").build();

        Employee savedEmployee = employeeRepository.save(employee);

        UUID id = savedEmployee.getId();

        Assertions.assertThat(employeeRepository.findById(id).get().getId()).isEqualTo(id);

    }

    @Test
    public void getEmployeeCepTest(){

       /* Employee employee = Employee.builder()
                .age(220)
                .name("Antonioaaa")
                .gender("Gender.MALEe")
                .cep(20551080)
                .adress("Some aAdress")
                .state("Some Staate")
                .street("Some Satreet")
                .city("Some Caity").build();

        Employee savedEmployee = employeeRepository.save(employee);

        UUID id = savedEmployee.getId();

        //List<Employee> cosa =employeeRepository.findByCep(20551070);

        //System.out.println(employeeRepository.findByCep(20551070).get(0).getCep());
        //Assertions.assertThat(employeeRepository.findByCep(20551070).get(0).getCep()).isEqualTo(20551070);
        */
    }

}
