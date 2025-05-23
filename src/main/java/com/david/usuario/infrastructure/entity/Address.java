package com.david.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private Long number;
    @Column(name = "complement", length = 10)
    private String complement;
    @Column(name = "city", length = 150)
    private String city;
    @Column(name = "state", length = 2)
    private String state;
    @Column(name = "cep", length = 9)
    private String cep;

}
