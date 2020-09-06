package com.example.paymentapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=555, message="must be equal or less than 555")
    @Column(name = "routeNumber", nullable = false)
    private Integer routeNumber;

    private LocalDate departureDate;

    private Status status;

    public Payment(Long id, Integer routeNumber, LocalDate departureDate) {
        this.id = id;
        this.routeNumber = routeNumber;
        this.departureDate = departureDate;
        this.status = null;
    }

}
