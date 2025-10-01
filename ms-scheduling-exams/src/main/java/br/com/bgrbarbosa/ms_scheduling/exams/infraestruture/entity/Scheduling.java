package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "tb_scheduling")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Scheduling {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID codScheduling;

    @Column
    private Status status;

    @Column
    private Double vlExam;

    @Column
    private Long protocol;

    @Column
    private String password;

    @Column
    private LocalDate date_exam;

    @Column
    private LocalDate date_result;

    @Column
    private String url_laudo;

    @ManyToOne
    @JoinColumn(name = "customer_uuid")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "tb_scheduling_exams",
            joinColumns = @JoinColumn(name = "scheduling_uuid"),
            inverseJoinColumns = @JoinColumn(name = "exam_uuid")
    )
    private List<Exam> examList;

    public Scheduling(UUID uuid, Status status, Double aDouble, LocalDate localDate, LocalDate localDate1, Customer customer, List<Exam> exams) {
    }

    @PrePersist
    public void initEntity() {
        this.setStatus(Status.OPEN);
        if (this.protocol == null) {
            this.protocol = generateUniqueProtocol();
        }

        if (this.password == null) {
            this.password = generateSecureRandomString(10);
        }
    }


    public Long generateUniqueProtocol() {
        long timestamp = Instant.now().toEpochMilli();
        int randomComponent = new Random().nextInt(1000);
        return timestamp * 1000 + randomComponent;
    }

    public String generateSecureRandomString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
