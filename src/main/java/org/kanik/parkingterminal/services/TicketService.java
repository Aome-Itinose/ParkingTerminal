package org.kanik.parkingterminal.services;

import lombok.Data;
import org.kanik.parkingterminal.store.entities.TicketEntity;
import org.kanik.parkingterminal.store.repositories.TicketRepository;
import org.kanik.parkingterminal.util.CostCalculator;
import org.kanik.parkingterminal.util.exceptions.AlreadyPaidTicketException;
import org.kanik.parkingterminal.util.exceptions.TicketNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Data
@Service
@Transactional(readOnly = true)
public class TicketService {
    private final TicketRepository repository;
    private final CostCalculator costCalculator;

    private final LocalDateTime NULL = LocalDateTime.of(1971, Month.JANUARY,1,0,0,0);

    public TicketEntity findTicketById(int id) {
        return repository.findById(id).orElseThrow(TicketNotFoundException::new);
    }
    public List<TicketEntity> findAll(){
        List<TicketEntity> tickets = repository.findAll();
        if(tickets.isEmpty()){
            throw new TicketNotFoundException();
        }
        return tickets;
    }
    @Transactional
    public TicketEntity createNewTicketAndGet() {
        TicketEntity newTicket = TicketEntity.builder()
                .entryTime(LocalDateTime.now())
                .build();
        return repository.save(newTicket);
    }
    @Transactional
    public TicketEntity createFineTicketAndGet(){
        TicketEntity ticket = TicketEntity.builder()
                .entryTime(NULL)
                .paid(false)
                .build();
        return repository.save(ticket);
    }
    @Transactional
    public int calculateCost(int id) {
        TicketEntity ticket = findTicketById(id);
        if(ticket.getEntryTime().equals(NULL) && !ticket.isPaid()){
            return costCalculator.getFine();
        }
        return costCalculator.calculate(ticket);
    }
    @Transactional
    public boolean payTicket(int id) {
        TicketEntity ticket = findTicketById(id);
        if(ticket.getEntryTime().isEqual(NULL)){
            ticket.setPayTime(LocalDateTime.now());
            ticket.setPaid(true);
            return true;
        }
        if (!ticket.isPaid()) {
            //Paying process
            ticket.setPaid(true);
            ticket.setPayTime(LocalDateTime.now());
            return true;
        }else{
            if(ticket.getPayTime().plusMinutes(costCalculator.getExitTime()).isAfter(LocalDateTime.now())){
                throw new AlreadyPaidTicketException();
            }
            else{
                ticket.setPayTime(LocalDateTime.now());
                //paying process
                return true;
            }
        }
    }
    @Transactional
    public boolean exit(int id) {
        if(canExit(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    private boolean canExit(int id){
        if (calculateCost(id) == 0) {
            return true;
        }
        TicketEntity ticket = findTicketById(id);
        if(!ticket.isPaid()){
            return false;
        }
        LocalDateTime exitAllowed = ticket.getPayTime().plusMinutes(costCalculator.getExitTime());
        if (exitAllowed.isAfter(LocalDateTime.now())) {
            //open doorп
            return true;
        }
        return false;
    }
}