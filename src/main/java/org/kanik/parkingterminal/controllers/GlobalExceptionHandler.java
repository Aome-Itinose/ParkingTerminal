package org.kanik.parkingterminal.controllers;

import org.kanik.parkingterminal.util.exceptions.AlreadyPaidTicketException;
import org.kanik.parkingterminal.util.exceptions.TicketNotFoundException;
import org.kanik.parkingterminal.util.exceptions.TicketNotPaidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TicketNotPaidException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    private boolean notPaid(TicketNotPaidException e){
        return false;
    }
    @ExceptionHandler(AlreadyPaidTicketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private boolean alreadyPaid(AlreadyPaidTicketException e){
        return false;
    }
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private boolean notPaid(TicketNotFoundException e){
        return false;
    }
}
