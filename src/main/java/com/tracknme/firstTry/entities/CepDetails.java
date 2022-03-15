package com.tracknme.firstTry.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "Cep")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CepDetails {

    @Id
    @Column
    private Integer cep;

    @Column
    private String logradouro;

    @Column
    private String complemento;

    @Column
    private String bairro;

    @Column
    private String localidade;

    @Column
    private String uf;

    @Column
    private String ibge;

    @Column
    private String gia;

    @Column
    private String ddd;

    @Column
    private String siafi;


}
