package com.ems_backend.jimmydev.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private String password;
    @Column(name="email",nullable = false,unique = true)

    private String email;

    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated
    @NotEmpty
    private Set<Roles> roles;


}
