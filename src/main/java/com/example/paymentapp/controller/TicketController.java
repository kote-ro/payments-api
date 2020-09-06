package com.example.paymentapp.controller;

import com.example.paymentapp.model.Status;
import com.example.paymentapp.model.Ticket;
import com.example.paymentapp.repository.TicketRepository;
import com.example.paymentapp.service.TicketRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketRepositoryService ticketRepositoryService;

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        List<Ticket> list = ticketRepositoryService
                .deleteDuplicatesByTicketId(ticketRepository.findAll());
        return list;
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Ticket ticket =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found on :: " + id));
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping("/tickets/status/{id}")
    public ResponseEntity<Status> getTicketStatusById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Ticket ticket =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found on :: " + id));
        Status status = ticket.getStatus();
        return ResponseEntity.ok().body(status);
    }
    // получение информации о билетах в будущем (>now)
    @GetMapping("/tickets/afternow")
    public List<Ticket> getAllAfterCurrentDate(){
        LocalDate now = LocalDate.now();
        return ticketRepository.findByDepartureDateAfter(now);
    }
    // создание билета
    @PostMapping("/tickets")
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) {
        try {
            Ticket _ticket = ticketRepository
                    .save(ticket);
            return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
