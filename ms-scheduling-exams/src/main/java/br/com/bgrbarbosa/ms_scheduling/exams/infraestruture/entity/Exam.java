package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_exam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam {

    @Id
    private UUID codExam;

    @Column
    private String descExam;

    @Column
    @JsonIgnore
    private Double vlExame;

    @Column
    @JsonIgnore
    private String orientationExam;
}
