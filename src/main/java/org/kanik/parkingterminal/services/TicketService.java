package org.kanik.parkingterminal.services;

import lombok.Data;
import org.kanik.parkingterminal.store.entities.TicketEntity;
import org.kanik.parkingterminal.store.repositories.TicketRepository;
import org.kanik.parkingterminal.util.CostCalculator;
import org.kanik.parkingterminal.util.exceptions.AlreadyPaidTicketException;
import org.kanik.parkingterminal.util.exceptions.TicketNotFoundException;
import org.kanik.parkingterminal.util.exceptions.TicketNotPaidException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Service
@Transactional(readOnly = true)
public class TicketService {
    private final TicketRepository repository;
    private final CostCalculator costCalculator;

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
    public int calculateCost(int id) {
        TicketEntity ticket = findTicketById(id);
        if (ticket.isPaid()) {
            ticket.setEntryTime(ticket.getPayTime());
            ticket.setPayTime(null);
            ticket.setPaid(false);
        }
        return costCalculator.calculate(ticket);
    }
    @Transactional
    public boolean payTicket(int id) {
        TicketEntity ticket = findTicketById(id);
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
                ticket.setEntryTime(ticket.getPayTime());
                ticket.setPayTime(LocalDateTime.now());
                //paying process
                return true;
            }
        }
    }
    @Transactional
    public boolean exit(int id) {
        TicketEntity ticket = findTicketById(id);
        LocalDateTime exitAllowed = ticket.getPayTime().plusMinutes(costCalculator.getExitTime());
        if (ticket.isPaid() && exitAllowed.isAfter(LocalDateTime.now())) {
            //open door
            repository.delete(ticket);
            return true;
        }else{
            throw new TicketNotPaidException();
        }
    }
}