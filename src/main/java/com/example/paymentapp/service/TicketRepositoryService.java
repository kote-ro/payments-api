package com.example.paymentapp.service;

import com.example.paymentapp.model.Ticket;
import com.example.paymentapp.model.Status;
import com.example.paymentapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketRepositoryService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketRepositoryService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findAll(){
        return this.ticketRepository.findAll();
    }

    public void updateStatus(Long id, Status status){
        ticketRepository.updateStatus(id, status);
    }

    public List<Ticket> findByDepartureDateAfter(){
        LocalDate now = LocalDate.now();
        return this.ticketRepository.findByDepartureDateAfter(now);
    }
    // удаляет дубликаты по ticket_id
    public List<Ticket> deleteDuplicatesByTicketId(List<Ticket> tickets){
        return tickets.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
