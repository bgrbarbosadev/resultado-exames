package br.com.bgrbarbosa.ms_exame.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_exam_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codExamType;

    @Column
    private String descExame;

    @OneToMany(mappedBy = "examType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exam> exams;

}
