package com.airline.ticketbookingapi.exception;

public class TicketNotFoundException extends ResourceNotFoundException{


    public TicketNotFoundException(String message) {
        super(message);
    }
}
