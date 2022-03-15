package com.tracknme.firstTry.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})//take out properties
@Builder
public class Employee  implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NonNull
    @Column(name = "cep", nullable = false)
    private Integer cep;

    @Column(name = "gender")
    private String gender;

    @Column(name = "adress")
    private String adress;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    public Employee(EmployeeDto employee) {
        this.name = employee.getName();

        this.age = employee.getAge();
        //edad mayor q 16

        this.cep = employee.getCep();
        //cep contem 8 numeros e uma formatacao
        this.gender = employee.getGender();
        this.adress = employee.getAdress();
        this.street = employee.getStreet();
        this.city = employee.getCity();
        this.state = employee.getState();
    }
}
