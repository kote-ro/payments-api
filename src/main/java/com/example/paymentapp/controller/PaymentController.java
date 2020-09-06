package com.example.paymentapp.controller;

import com.example.paymentapp.model.Payment;
import com.example.paymentapp.model.Status;
import com.example.paymentapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Payment payment =
                paymentRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Payment not found on :: " + id));
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/payments/status/{id}")
    public ResponseEntity<Status> getPaymentStatusById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Payment payment =
                paymentRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Payment not found on :: " + id));
        Status status = payment.getStatus();
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        try {
            Payment _payment = paymentRepository
                    .save(payment);
            return new ResponseEntity<>(_payment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
