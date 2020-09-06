package com.example.paymentapp.service;

import com.example.paymentapp.model.Payment;
import com.example.paymentapp.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentProcessingService {
    // каждые 60 секунд вызывать | синтакис (*/sec min hour day month day of week)
    private static final String CRON = "*/60 * * * * *";

    @Autowired
    private PaymentRepositoryService repositoryService;
    // фоновый процесс обработки платежей
    @Scheduled(cron = CRON)
    public void processPaymentsFromDB() {
        List<Payment> list = repositoryService.findAll();
        // ДЛЯ ОДНОГО РАНДОМНОГО ПЛАТЕЖА МЕНЯТЬ РАЗ В МИНУТУ СТАТУС
        if (!list.isEmpty()) {
            changeStatus(list);
        }
    }
    // логика измения статуса платежа
    public void changeStatus(List<Payment> list){

        for(Payment payment : list){
            try {
                if (payment.getStatus() == null) {
                    payment.setStatus(Status.randomStatus());
                    repositoryService.updateStatus(payment.getId(), payment.getStatus());
                    log.info("Changed payment info, id = " + payment.getId()
                            + ", route number = " + payment.getRouteNumber()
                            + ", status = " + payment.getStatus());
                    break;
                }
            }catch (Exception e){
                log.error("Error in processing payment with id ", payment.getId(), e.getMessage());
                log.error("More precise description of error : ", e);
            }
        }
    }

}
