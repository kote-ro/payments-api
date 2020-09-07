package com.example.paymentapp.service;

import com.example.paymentapp.model.Ticket;
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
public class TicketProcessingService {
    // каждые 60 секунд вызывать | синтакис (*/sec min hour day month day of week)
    private static final String CRON = "*/60 * * * * *";

    @Autowired
    private TicketRepositoryService repositoryService;
    // фоновый процесс обработки платежей
    @Scheduled(cron = CRON)
    public void processPaymentsFromDB() {
        List<Ticket> list = repositoryService.findAll();
        if (!list.isEmpty()) {
            changeStatus(list);
        }
    }
    /* логика измения статуса платежа :
    *  присвоение временного статуса STARTED_PROCESS + сохранение значения ->
    *  -> возникновение ошибки/остановка приложения ->
    *  -> присвоение конечного статуса билету(IN_PROCESS/ERROR/COMPLETED) + сохранение значения
     */
    public void changeStatus(List<Ticket> list){

        for(Ticket ticket : list){
            try {
                if (ticket.getStatus() == null || ticket.getStatus() == Status.STARTED_PROCESS) {
                    ticket.setStatus(Status.STARTED_PROCESS);
                    repositoryService.updateStatus(ticket.getId(), ticket.getStatus());
                    /*
                    * место потенциальной ошибки во время изменения статуса
                    */
                    ticket.setStatus(Status.randomStatus());
                    repositoryService.updateStatus(ticket.getId(), ticket.getStatus());
                    log.info("Changed ticket info, id = " + ticket.getId()
                            + ", route number = " + ticket.getRouteNumber()
                            + ", status = " + ticket.getStatus());
                    break;
                }
            }catch (Exception e){
                log.error("Error in processing ticket with id ", ticket.getId(), e.getMessage());
                log.error("More precise description of error : ", e);
            }
        }
    }

}
