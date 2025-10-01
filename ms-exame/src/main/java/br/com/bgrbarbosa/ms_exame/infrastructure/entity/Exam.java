package br.com.bgrbarbosa.ms_exame.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_exam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codExam;

    @Column
    private String descExam;

    @Column
    private Double vlExame;

    @Column
    private String orientationExam;

    @ManyToOne
    @JoinColumn(name = "cod_exam_type")
    @JsonIgnore
    private ExamType examType;


}
