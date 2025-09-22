package com.airline.ticketbookingapi.exception;

public class ReservationNotFoundException extends ResourceNotFoundException{


    public ReservationNotFoundException(String message) {
        super(message);
    }
}
