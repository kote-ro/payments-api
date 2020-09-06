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
@Table(name = "tickets")
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ticketId;

    private Long clientId;

    @NotNull
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=555, message="must be equal or less than 555")
    @Column(name = "routeNumber", nullable = false)
    private Integer routeNumber;

    private LocalDate departureDate;

    private Status status;

    public Ticket(Long id, Long ticketId, Long clientId, Integer routeNumber, LocalDate departureDate) {
        this.id = id;
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.routeNumber = routeNumber;
        this.departureDate = departureDate;
        this.status = null;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Ticket)
                && (((Ticket) o).getTicketId()).equals(this.getTicketId());
    }

    @Override
    public int hashCode() {
        return ticketId.hashCode();
    }
}
