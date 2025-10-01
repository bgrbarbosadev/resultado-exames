package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID codCustomer;

    @Column
    private String name;

    @Column
    @JsonIgnore
    private String cpf;

    @Column
    @JsonIgnore
    private String email;

    @Column
    @JsonIgnore
    private String tel;

    @Column
    @JsonIgnore
    private String cel;

    @Column
    @JsonIgnore
    private String address;

    @Column
    @JsonIgnore
    private String neighborhood;

    @Column
    @JsonIgnore
    private String city;

    @Column
    @JsonIgnore
    private String state;

    @Column
    @JsonIgnore
    private String cep;

    @JsonIgnore
    private LocalDate date_insert;

    @JsonIgnore
    private LocalDate date_update;

    @PrePersist
    protected void onCreate() {
        date_insert = LocalDate.now();
        date_update = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        date_update = LocalDate.now();
    }
}
