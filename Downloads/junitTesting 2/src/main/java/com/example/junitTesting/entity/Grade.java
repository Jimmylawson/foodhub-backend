package com.example.junitTesting.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String subject;
    private String letter;
    private double score;
   @ManyToOne
   @JoinColumn(name="student_id")
   @JsonBackReference
    private Student student;
}
