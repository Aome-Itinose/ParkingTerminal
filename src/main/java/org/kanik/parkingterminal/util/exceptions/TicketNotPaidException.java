package org.kanik.parkingterminal.util.exceptions;

public class TicketNotPaidException extends RuntimeException{
    public TicketNotPaidException(){
        super("Ticket is not paid");
    }
    public TicketNotPaidException(String msg){
        super(msg);
    }
}
