package com.example.paymentapp.service;

import com.example.paymentapp.model.Payment;
import com.example.paymentapp.model.Status;
import com.example.paymentapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRepositoryService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentRepositoryService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll(){
        return this.paymentRepository.findAll();
    }

    public void updateStatus(Long id, Status status){
        paymentRepository.updateStatus(id, status);
    }
}
