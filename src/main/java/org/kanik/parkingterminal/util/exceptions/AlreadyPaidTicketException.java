package org.kanik.parkingterminal.util.exceptions;

public class AlreadyPaidTicketException extends RuntimeException {
    public AlreadyPaidTicketException() {
        super("Ticket is already paid");
    }

    public AlreadyPaidTicketException(String msg) {
        super(msg);
    }
}
