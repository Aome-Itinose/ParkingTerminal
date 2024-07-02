package org.kanik.parkingterminal.util.exceptions;

import org.kanik.parkingterminal.store.repositories.TicketRepository;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(){
        super("Ticket not found");
    }
    public TicketNotFoundException(String msg){
        super(msg);
    }
}
